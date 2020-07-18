import { CustomMultipartFile } from './customMultipartFile';
import { UserType } from './userType';
import { UserRoleGroup } from './userRoleGroup';
import { PortalType } from './portalType';

export class UserProfile {
    id: number;
    userId: string;
    firstName: string;
    lastName: string;
    dob: string;
    gender: string;
    email: string;
    contactNo: string;
    userType: UserType;
    userRoleGroup: UserRoleGroup;
    userRoleGroupCode: string;
    userGroupRoleBranchCode: string;
    status: string;
    docMgtId: string;
    fileUploads: CustomMultipartFile[];
    isAdmin: string;
    cntryCd: string;
    position: string;
    nationalId: string;
    portalType: PortalType;
    authOption: string;
}
