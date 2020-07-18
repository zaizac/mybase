import { IdmConfigDto } from './idmconfigdto';

export class IdmConfigMapper {
  private portalMode = '_PORTAL_MODE';
  private portalLayout = '_PORTAL_LAYOUT';
  private portalTheme = '_PORTAL_THEME';
  private portalTexture = '_PORTAL_TEXTURE';
  private portalContent = '_PORTAL_CONTENT';
  private portalPage = '_PORTAL_PAGE';
  private portalMenu = '_PORTAL_MENU';

  constructor(private portalType: string, private idmConfigDtos: IdmConfigDto[]) {
    portalType = portalType.toUpperCase();
    this.portalMode = portalType + this.portalMode;
    this.portalLayout = portalType + this.portalLayout;
    this.portalTheme = portalType + this.portalTheme;
    this.portalTexture = portalType + this.portalTexture;
    this.portalContent = portalType + this.portalContent;
    this.portalPage = portalType + this.portalPage;
    this.portalMenu = portalType + this.portalMenu;
  }

  getThemeSwitcherConfig() {
    this.portalType = this.portalType.toUpperCase();
    // similar to theme-switcher class in ui-master
    const themeConfig = {
        args: {
        color: 'green',
        showSettings: false,
        showMinisidebar: false,
        showDarktheme: false,
        showRtl: false,
        showMenu: 'left'
        }
    };

    // check idmConfigDtos valid
    if (Array.isArray(this.idmConfigDtos) && this.idmConfigDtos.length > 0) {
      this.idmConfigDtos.forEach(element => {
        switch (element.configCode) {
          case this.portalContent: {
             break;
          }
          case this.portalLayout: {
            // horizonal | left | right
            if (element.configVal.includes('right')) {
              themeConfig.args.showRtl = true;
            }
            break;
          }
          case this.portalMode: {
            if (element.configVal === 'dark') {
              themeConfig.args.showDarktheme = true;
            } else {
              themeConfig.args.showDarktheme = false;
            }
            break;
          }
          case this.portalPage: {
            break;
          }
          case this.portalTexture: {
            // json.args.color = element.configVal;
            break;
          }
          case this.portalTheme: {
            themeConfig.args.color = element.configVal;
            break;
          }
          case this.portalMenu: {
            themeConfig.args.showMenu = element.configVal;
          }
        }
      });
    }
    return themeConfig.args;
  }

  getIdmConfig(color: string, showSettings: boolean,
    showMinisidebar: boolean, showDarktheme: boolean, showRtl: boolean, showMenu?: string): IdmConfigDto [] {
    // check idmConfigDtos valid
    if (Array.isArray(this.idmConfigDtos) && this.idmConfigDtos.length > 0) {
      for (let element of this.idmConfigDtos) {
        switch (element.configCode) {
          case this.portalContent: {
             break;
          }
          case this.portalLayout: {
            // horizonal | left | right
            if (showRtl) {
              element.configVal = 'right';
            } else {
              element.configVal = 'horizontal';
            }
            break;
          }
          case this.portalMode: {
            if (showDarktheme) {
              element.configVal = 'dark';
            } else {
              element.configVal = 'light';
            }
            break;
          }
          case this.portalPage: {
            break;
          }
          case this.portalTexture: {
            element.configVal = color;
            break;
          }
          case this.portalTheme: {
            element.configVal = color;
            break;
          }
          case this.portalMenu: {
            element.configVal = showMenu;
            break;
          }
          default: {
            break;
          }
        }
      }
    }
    return this.idmConfigDtos;
  }
}
