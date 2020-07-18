import { LangDesc } from './langdesc';

export class PortalType {
    portalTypeCode?: string;
    portalTypeDesc?: LangDesc;

    constructor(args?: {
        portalTypeCode?: string,
        portalTypeDesc?: LangDesc
    }) {
        this.portalTypeCode = args.portalTypeCode;
        this.portalTypeDesc = args.portalTypeDesc;
   }
}
