// ==============================================================
// This is configuration script file. DO NOT UPDATE
// ==============================================================

const fs = require('fs-extra');
const path = require('path');
const Bundler = require('scss-bundle').Bundler;

(async () => {
  const sources = 'projects/ui-master/src/resources';
  const destination = 'dist/ui-master/resources';

  // copy source folder to destination
  await fs.copy(sources, destination, (err: any) => {
    if (err) {
      console.log('An error occured while copying the folder.');
      return console.error(err);
    }
    console.log('Copy completed!');
    // Copy necessary fonts
    const fontDestination = 'dist/ui-master/resources/fonts';
    const fontSources = 'src/assets/scss/icons/font-awesome/fonts';
    // copy font-awesome fonts
    fs.copy(fontSources, fontDestination, (err: any) => {
      if (err) {
        console.log('An error occured while copying the folder.');
        return console.error(err);
      }
      console.log('Copy completed for font-awesome fonts!');
      // copy material-design-iconic-font
      copyFilesIntoExistingFolder(
        'src/assets/scss/icons/material-design-iconic-font/fonts',
        fontDestination,
        'material-design-iconic-font'
      );

      // copy simple-line-icons
      copyFilesIntoExistingFolder(
        'src/assets/scss/icons/simple-line-icons/fonts',
        fontDestination,
        'simple-line-icons'
      );
      bundleScss();
    });
  });
})();

async function copyFilesIntoExistingFolder(fontSourceFolder: string, fontDestFolder: string, logName: string) {
  const files = fs.readdirSync(fontSourceFolder);
  for (const file of files) {
    await fs.copy(fontSourceFolder + '/' + file, fontDestFolder + '/' + file, (err: any) => {
      if (err) {
        console.log('An error occured while copying the files.');
        return console.error(err);
      }
    });
  }
  const outputName = logName ? logName : 'files';
  console.log('Copy completed for ' + outputName);
}

async function bundleScss() {
  const { found, bundledContent, imports } = await new Bundler().Bundle('./src/styles.scss', ['./src/**/*.scss']);

  if (imports) {
    const cwd = process.cwd();

    const filesNotFound = imports.filter(x => !x.found).map(x => path.relative(cwd, x.filePath));

    if (filesNotFound.length) {
      console.error(`SCSS imports failed \n\n${filesNotFound.join('\n - ')}\n`);
      throw new Error('One or more SCSS imports failed');
    }
  }

  if (found) {
    fs.ensureDirSync('./dist/ui-master/resources/scss');
    await fs.writeFile('./dist/ui-master/resources/scss/ui-master.scss', bundledContent);
    console.log('SCSS bundle success');
    // copy themify-icons
    copyFilesIntoExistingFolder(
      'src/assets/scss/icons/themify-icons/fonts',
      'dist/ui-master/resources/scss/fonts',
      'themify-icons'
    );
    const sources2 = 'src/assets/images';
    const destination2 = 'dist/ui-master/resources/scss/assets/images';
    // copy Assets for admin pro scss
    await fs.copy(sources2, destination2, (err: any) => {
      if (err) {
        // Folder should not existed. However, if error, we try to copy files by treating it as existing folder
        copyFilesIntoExistingFolder(sources2, destination2, 'Admin Pro Assets');
      } else {
        console.log('Copy completed for Admin Pro Assets!');
      }
    });
  }
}
