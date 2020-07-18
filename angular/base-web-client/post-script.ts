// ==============================================================
// This is configuration script file. DO NOT UPDATE
// ==============================================================

let fs = require('fs-extra');

let source = 'node_modules/ui-master/resources';
let destination = 'src/assets/default';

fs.remove(destination, err => {
    console.log('site-web assets > Copying resources from ' + source + ' to ' + destination + ' if exists ....');
    if (err) { return console.error('site-web assets > ' + err); }
    console.log('site-web assets > Remove completed!');
    // copy source folder to destination
    fs.copy(source, destination, err => {
        if (err) {
            console.log('site-web assets > An error occured while copying the folder.');
            return console.error(err);
        }
        console.log('site-web assets > Copy completed!');
    });
});

