import { UserType } from './userType';

export class UserGroup{
    id? : number;
	userGroupCode? : string;
	userRoleGroupCode? : string;
	parentRoleGroup? : string;
	maxNoOfUser? : number;
	userRoleGroupDesc? : string;
	crtUsrId? : string;
	modUsrId? : string;
	crtTs? : Date; 
	modTs? : Date;
	userType? : UserType;
	userGroupDesc? : string;
	userTypeDesc? : string;
	userTypeCode? : string;
	selCountry? : boolean;
	selBranch?  : boolean;
}