import { PortalType } from './portalType';
import { LangDesc } from './langdesc';

export class UserRole {
    roleCode?: string;
    roleDesc?: LangDesc;
    portalType?: PortalType;

    constructor(args?: {
      roleCode?: string;
      roleDesc?: LangDesc;
      portalType?: PortalType;
    }) {
        this.roleCode = args.roleCode;
        this.roleDesc = args.roleDesc;
        this.portalType = args.portalType;
    }
}
