import { LangDesc } from './langdesc';

export class UserMenu {
    menuCode?: string;
    menuParentCode?: string;
    menuDesc?: LangDesc;
    menuLevel?: number;
    menuSequence?: number;
    menuUrlCd?: string;
    menuIcon?: string;
    menuChild?: UserMenu[];
}