'use strict';

var path = require('path');
var gulp = require('gulp');
var conf = require('./conf');

var browserSync = require('browser-sync');

var $ = require('gulp-load-plugins')();

gulp.task('scripts', function() {
    return gulp.src(path.join(conf.paths.src, '/app/**/*.js'))
        .pipe($.eslint())                   // 语法检查
        .pipe($.eslint.format())
        .pipe(browserSync.reload({
            stream: true
        }))
        .pipe($.size());
});
