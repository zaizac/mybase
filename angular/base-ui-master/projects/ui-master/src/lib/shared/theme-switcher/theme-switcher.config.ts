export class ThemeSwitcher {
  color: string;
  menu?: string;
  showSettings: boolean;
  showMinisidebar: boolean;
  showDarktheme: boolean;
  showRtl: boolean;

  constructor(args: {
      color: string,
      menu?: string,
      showSettings: boolean,
      showMinisidebar: boolean,
      showDarktheme: boolean,
      showRtl: boolean
  }) {
      this.color = args.color;
      this.menu = (args.menu)? args.menu : 'left';
      this.showSettings = args.showSettings;
      this.showMinisidebar = args.showMinisidebar;
      this.showDarktheme = args.showDarktheme;
      this.showRtl = args.showRtl;    
  }
  
}