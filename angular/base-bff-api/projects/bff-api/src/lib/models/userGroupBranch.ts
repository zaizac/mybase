import { UserType } from './userType';

export class UserGroupBranch {
  branchId?: number;
  userType?: UserType;
  userGroupCode?: string;
  branchCode?: string;
  branchName?: string;
  branchDept?: string;
  addr1?: string;
  addr2?: string;
  addr3?: string;
  addr4?: string;
  addr5?: string;
  cityCd?: string;
  stateCd?: string;
  cntryCd?: string;
  zipcode?: string;
  email?: string;
  faxNo?: string;
  contactNo?: string;
  docRefNo?: string;
  status?: boolean;

  constructor(args?: {
    branchId?: number,
    userType?: UserType,
    userGroupCode?: string,
    branchCode?: string,
    branchName?: string,
    branchDept?: string,
    addr1?: string,
    addr2?: string,
    addr3?: string,
    addr4?: string,
    addr5?: string,
    cityCd?: string,
    stateCd?: string,
    cntryCd?: string,
    zipcode?: string,
    email?: string,
    faxNo?: string,
    contactNo?: string,
    docRefNo?: string,
    status?: boolean
  }) {
    this.branchId = args.branchId;
    this.userType = args.userType;
    this.userGroupCode = args.userGroupCode;
    this.branchCode = args.branchCode;
    this.branchName = args.branchName;
    this.branchDept = args.branchDept;
    this.addr1 = args.addr1;
    this.addr2 = args.addr2;
    this.addr3 = args.addr3;
    this.addr4 = args.addr4;
    this.addr5 = args.addr5;
    this.cityCd = args.cityCd;
    this.stateCd = args.stateCd;
    this.cntryCd = args.cntryCd;
    this.zipcode = args.zipcode;
    this.email = args.email;
    this.faxNo = args.faxNo;
    this.contactNo = args.contactNo;
    this.docRefNo = args.docRefNo;
    this.status = args.status;
  }

}
