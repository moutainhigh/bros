'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var browserSync = require('browser-sync');

function isOnlyChange(event) {
    return event.type === 'changed';
}

gulp.task('watch', ['inject'], function() {

    gulp.watch([path.join(conf.paths.src, '/*.html'), 'bower.json'], ['inject']);

    // 监测样式变化
    gulp.watch([
        path.join(conf.paths.src, '/app/**/*.css'),
        path.join(conf.paths.src, '/app/**/*.styl')
    ], function(event) {
        if (isOnlyChange(event)) {
            gulp.start('styles');
        } else {
            gulp.start('inject');
        }
    });

    // 监测除配置文件之外的js文件变化
    gulp.watch([
        path.join(conf.paths.src, '/app/**/*.js'),
        path.join('!' + conf.paths.src, '/app/index.*.js')
    ], function(event) {
        if (isOnlyChange(event)) {
            gulp.start('scripts');
        } else {
            gulp.start('inject');
        }
    });

    // 监测html文件变化
    gulp.watch(path.join(conf.paths.src, '/app/**/*.html'), function(event) {
        browserSync.reload(event.path);
    });
});
