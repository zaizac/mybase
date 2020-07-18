import { Token } from './token';
import { UserRole } from './userRole';
import { UserMenu } from './userMenu';
import { UserType } from './userType';
import { UserRoleGroup } from './userRoleGroup';

export class User {
    id: number;
    userId: string;
    username: string;
    password: string;
    firstName: string;
    lastName: string;
    status: string;
    dob: Date;
    gender: string;
    email: string;
    contactNo: string;
    userType: UserType;
    portalType: string;
    userRoleGroup: UserRoleGroup;
    userRoleGroupCode: string;
    userGroupRoleBranchCode: string;
    cntryCd: string;
    profId: BigInteger;
    branchId: BigInteger;
    docMgtId: string;
    orgRegNo: string;
    token: Token;
    roles: UserRole[];
    menus: UserMenu[];
    authOption: string;

    constructor(args: {
        id: number,
        userId: string,
        username: string,
        password: string,
        firstName: string,
        lastName: string,
        status: string,
        dob: Date,
        gender: string,
        email: string,
        contactNo: string,
        userType: UserType,
        portalType: string,
        userRoleGroup: UserRoleGroup,
        userRoleGroupCode: string,
        userGroupRoleBranchCode: string;
        cntryCd: string,
        docMgtId: string,
        profId: BigInteger,
        branchId: BigInteger,
        orgRegNo: string,
        token: Token,
        roles: UserRole[],
        menus: UserMenu[],
        authOption: string
    }) {
        this.id = args.id;
        this.userId = args.userId;
        this.username = args.username;
        this.password = args.password;
        this.firstName = args.firstName;
        this.lastName = args.lastName;
        this.dob = args.dob;
        this.gender = args.gender;
        this.email = args.email;
        this.contactNo = args.contactNo;
        this.userType = args.userType;
        this.portalType = args.portalType;
        this.userRoleGroup = args.userRoleGroup;
        this.userRoleGroupCode = args.userRoleGroupCode;
        this.userGroupRoleBranchCode = args.userGroupRoleBranchCode;
        this.cntryCd = args.cntryCd;
        this.docMgtId = args.docMgtId;
        this.profId = args.profId;
        this.branchId = args.branchId;
        this.orgRegNo = args.orgRegNo;
        this.token = args.token;
        this.roles = args.roles;
        this.menus = args.menus;
        this.authOption = args.authOption;
    }

}