import { LangDesc } from './langdesc';

export class UserType {
    userTypeCode: string;
    userTypeDesc?: LangDesc;
    emailAsUserId?: boolean;

    constructor(args: {
      userTypeCode: string;
      userTypeDesc?: LangDesc;
      emailAsUserId?: boolean;
    }) {
        this.userTypeCode = args.userTypeCode;
        this.userTypeDesc = args.userTypeDesc;
        this.emailAsUserId = args.emailAsUserId;
    }

}
