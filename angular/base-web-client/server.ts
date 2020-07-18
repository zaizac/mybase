import 'zone.js/dist/zone-node';
import {enableProdMode} from '@angular/core';
// Express Engine
import {ngExpressEngine} from '@nguniversal/express-engine';
// Import module map for lazy loading
import {provideModuleMap} from '@nguniversal/module-map-ngfactory-loader';

import * as express from 'express';
import {join} from 'path';

// // i18n
// import { TRANSLATIONS, TRANSLATIONS_FORMAT, LOCALE_ID  } from '@angular/core';
// import { TRANSLATION_FR } from './src/locale/messages.fr.xlf';
// import { TRANSLATION_MS } from './src/locale/messages.ms.xlf';


// Faster server renders w/ Prod mode (dev mode never needed)
enableProdMode();

// Express server
const app = express();

const PORT = process.env.PORT || 4000;
const DIST_FOLDER = join(process.cwd(), 'dist');

const servers = [
    {
        name: 'site-web',
        server: express(),
        port: 4000,
        supportedLocales: ['ms', 'en', 'ta', 'bn'],
        folder: join(DIST_FOLDER, 'site-web')
    }
    // ,
    // {
    //     name: 'site2',
    //     server: express(),
    //     port: 4001,
    //     supportedLocales: ['it', 'en', 'es'],
    //     folder: join(DIST_FOLDER, 'site2')
    // }
];

// * NOTE :: leave this as require() since this file is built Dynamically from webpack
const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/server/main');

// // Our Universal express-engine (found @ https://github.com/angular/universal/tree/master/modules/express-engine)
// app.engine('html', ngExpressEngine({
//   bootstrap: AppServerModuleNgFactory,
//   providers: [
//     provideModuleMap(LAZY_MODULE_MAP)
//   ]
// }));

// app.set('view engine', 'html');
// app.set('views', DIST_FOLDER);

// // Example Express Rest API endpoints
// // app.get('/api/**', (req, res) => { });
// // Serve static files from /browser
// app.get('*.*', express.static(DIST_FOLDER, {
//   maxAge: '1y'
// }));

// // All regular routes use the Universal engine
// app.get('*', (req, res) => {
//   res.render('index', { req });
// });

// // Start up the Node server
// app.listen(PORT, () => {
//   console.log(`Node Express server listening on http://localhost:${PORT}`);
// });

// Start up the Node server
// app.listen(PORT, () => {
//     console.log(`Node Express server listening on http://localhost:${PORT}`);
// });
// ----------------------------------

servers.map(app => {

    // Our Universal express-engine (found @ https://github.com/angular/universal/tree/master/modules/express-engine)
    app.server.engine('html', ngExpressEngine({
        bootstrap: AppServerModuleNgFactory,
        providers: [
            provideModuleMap(LAZY_MODULE_MAP),
        ]
    }));

    app.server.set('view engine', 'html');
    app.server.set('views', app.folder);

    // Server static files from
    app.server.get('*.*', express.static(app.folder, {maxAge: '1y'}));

    app.server.get('*', (req, res) => {
        // this is for i18n
        const defaultLocale = 'en';
        const matches = req.url.match(/^\/([a-z]{2}(?:-[A-Z]{2})?)\//);

        // check if the requested url has a correct format '/locale' and matches any of the supportedLocales
        const locale = (matches && app.supportedLocales.indexOf(matches[1]) !== -1) ? matches[1] : defaultLocale;

        // ----------------- fix for node error - start -----------------
        // refer to https://stackoverflow.com/questions/52391718/angular-6-server-side-rendering-issue-window-not-defined
        const domino = require('domino');
        const fs = require('fs');
        const path = require('path');
        const localStorage = require('localStorage');
        const formData = require('form-data');
        // const Buffer = require('safer-buffer').
        // global.Buffer = global.Buffer || require('buffer').Buffer;
        // const Buffer = require('safer-buffer').Buffer;

        const templateA = fs
            .readFileSync(path.join(app.folder, locale, 'index.html'))
            .toString();
        const win = domino.createWindow(templateA);
        win.Object = Object;
        win.Math = Math;

        global['window'] = win;
        global['document'] = win.document;
        global['branch'] = null;
        global['object'] = win.object;
        global['HTMLElement'] = win.HTMLElement;
        global['navigator'] = win.navigator;
        global['localStorage'] = localStorage;
        global['FormData'] = formData;
        // global['Buffer'] = global.Buffer;
        global['Buffer'] = require('safer-buffer').Buffer;
        global['btoa'] = function (str) { return Buffer.from(str).toString('base64'); };
        global['atob'] = function (str) { return Buffer.from(str, 'base64').toString(); };

        // ----------------- fix for node error - end -----------------

        let ip = req.headers['x-forwarded-for'] || req.connection.remoteAddress;
        if (ip.slice(0, 7) === '::ffff:') {
            ip = ip.slice(7);
        }

        res.render(`${locale}/index`, {
            req,
            providers: [
                {provide: 'language', useFactory: () => locale, deps: []},
                {provide: 'ip', useFactory: () => ip, deps: []}
            ]
        });
    });



    // Start up the Node server
    app.server.listen(app.port, () => {
        console.log(`Node Express server for ${app.name} listening on http://localhost:${app.port}`);
    });
});
