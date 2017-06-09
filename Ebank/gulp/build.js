'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var $ = require('gulp-load-plugins')({
    pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license', 'del']
});

gulp.task('partials', function() {
    return gulp.src([
            path.join(conf.paths.src, '/app/**/*.html'),
            path.join(conf.paths.tmp, '/serve/app/**/*.html')
        ])
        .pipe($.minifyHtml({
            empty: true,
            spare: true,
            quotes: true
        }))
        .pipe($.angularTemplatecache('templateCacheHtml.js', {
            module: 'EBankProject',
            root: 'app'
        }))
        .pipe(gulp.dest(conf.paths.tmp + '/partials/'));
});

gulp.task('html', ['inject', 'partials'], function() {
    var partialsInjectFile = gulp.src(path.join(conf.paths.tmp, '/partials/templateCacheHtml.js'), {
        read: false
    });
    var partialsInjectOptions = {
        starttag: '<!-- inject:partials -->',
        ignorePath: path.join(conf.paths.tmp, '/partials'),
        addRootSlash: false
    };

    var htmlFilter = $.filter('*.html', {
        restore: true
    });

    var jsFilter = $.filter('**/*.js', {
        restore: true
    });

    var cssFilter = $.filter('**/*.css', {
        restore: true
    });
    var assets;

    return gulp.src(path.join(conf.paths.tmp, '/serve/*.html'))
        .pipe($.inject(partialsInjectFile, partialsInjectOptions))
        .pipe(assets = $.useref.assets({
            noconcat: false
        }))                                         //
        .pipe($.rev())                              // Static asset revisioning by appending content hash to filenames
        .pipe(jsFilter)                             // filter a subset of the files (app.js and vendor.js)
        .pipe($.sourcemaps.init())                  //
        .pipe($.ngAnnotate())                       //
        // .pipe($.uglify({ preserveComments: $.uglifySaveLicense })).on('error', conf.errorHandler('Uglify'))
        .pipe($.sourcemaps.write('maps'))           // write files into dist/maps
        .pipe(jsFilter.restore)                     // restore files back to filefolder
        .pipe(cssFilter)                            //
        .pipe($.sourcemaps.init())                  //
        .pipe($.replace('../../bower_components/bootstrap-stylus/fonts/', '../fonts/'))
        .pipe($.minifyCss({
            processImport: false
        }))                                         //
        .pipe($.sourcemaps.write('maps'))           // write files into dist/maps
        .pipe(cssFilter.restore)                    //
        .pipe(assets.restore())                     //
        .pipe($.useref())                           //
        .pipe($.revReplace())                       //
        .pipe(htmlFilter)                           //
        .pipe($.minifyHtml({
            empty: true,
            spare: true,
            quotes: true,
            conditionals: true
        }))                                         //
        .pipe(htmlFilter.restore)                   //
        .pipe(gulp.dest(path.join(conf.paths.dist, '/')))               // write all files into dist filefolder
        .pipe($.size({
            title: path.join(conf.paths.dist, '/'),
            showFiles: true                         // displays the size of every file instead of just the total size.
        }));                                        // display the size of your project (not necessary)
});

// Only applies for fonts from bower dependencies
// Custom fonts are handled by the "other" task
gulp.task('fonts', function() {
    return gulp.src($.mainBowerFiles().concat('bower_components/bootstrap-stylus/fonts/*'))
        .pipe($.filter('**/*.{eot,svg,ttf,woff,woff2}'))
        .pipe($.flatten())
        .pipe(gulp.dest(path.join(conf.paths.dist, '/fonts/')));
});

gulp.task('other', function() {
    var fileFilter = $.filter(function(file) {
        return file.stat.isFile();
    });

    return gulp.src([
            path.join(conf.paths.src, '/**/*'),
            path.join('!' + conf.paths.src, '/**/*.{html,css,js,styl}')
        ])
        .pipe(fileFilter)
        .pipe(gulp.dest(path.join(conf.paths.dist, '/')));
});

gulp.task('clean', function() {
    return $.del([path.join(conf.paths.dist, '/'), path.join(conf.paths.tmp, '/')]);   // delete dist filefolder and .tmp filefolder
});

gulp.task('build', ['html', 'fonts', 'other']);
