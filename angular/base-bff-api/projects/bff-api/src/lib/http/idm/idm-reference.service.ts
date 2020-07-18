import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BffApiConfig } from '../../bff-api.config';
import { Observable } from 'rxjs';
import { UserType, UserMenu, UserGroup, UserRole, RoleMenu, IdmConfigDto, UserGroupRole, UserGroupBranch } from '../../models';
import { NGXLogger } from 'ngx-logger';
import { OauthClientDetails } from '../../models/oauthClientDetails';

@Injectable({
  providedIn: 'root'
})
export class IdmReferenceService {

  apiRef = '/api/idm';
  apiRefUrl: string;

  constructor(
    private http: HttpClient,
    private config: BffApiConfig,
    private logger: NGXLogger) { this.apiRefUrl = this.config.url + this.apiRef; }


  retrieveUserTypes(): Observable<UserType[]> {
    return this.http.get<UserType[]>(this.apiRefUrl + '/user-type');
  }

  createUserType(userType: UserType): Observable<[]> {
    this.logger.debug('Create userType:  [', userType, ']');
    return this.http.post<[]>((this.apiRefUrl + '/user-type/create'), userType);
  }

  updateUserType(userType: UserType): Observable<[]> {
    this.logger.debug('Update userType:  [', userType, ']');
    return this.http.post<[]>((this.apiRefUrl + '/user-type/update'), userType);
  }

  deleteUserType(userType: UserType): Observable<[]> {
    this.logger.debug('Update userType:  [', userType, ']');
    return this.http.post<[]>((this.apiRefUrl + '/user-type/delete'), userType);
  }

  searchByUserTypeCd(userTypeCd: string): Observable<UserType> {
    const params = { params: { 'userTypeCode': userTypeCd } };
    return this.http.get<UserType>(this.apiRefUrl + '/user-type/findByUserTypeCd', Object.assign({}, params));
  }

  searchPaginated(payload: any) {
    //payload.portalAdmin = isPortalAdmin;
    const params = { params: payload };
    return this.http
      .post(
        this.apiRefUrl + '/user-type/paginated',
        payload
      );
  }

  /*** IDM MENU ***/
  createMenu(userMenu: UserMenu): Observable<[]> {
    return this.http.post<[]>((this.apiRefUrl + '/menu/addMenu'), userMenu);
  }

  updateMenu(userMenu: UserMenu): Observable<[]> {
    return this.http.post<[]>((this.apiRefUrl + '/menu/updateMenu'), userMenu);
  }

  searchMenu(userMenu: UserMenu): Observable<UserMenu[]> {
    return this.http.post<UserMenu[]>((this.apiRefUrl + '/menu/searchMenu'), userMenu);
  }

  findMenuBymenuCode(menuCode: string): Observable<UserMenu> {
    const params = { params: { 'menuCode': menuCode } };
    return this.http.get<UserMenu>(this.apiRefUrl + '/menu/menuCode', Object.assign({}, params));
  }

  findMenuByMenuLevel(menuLevel: any): Observable<UserMenu[]> {
    const params = { params: { 'menuLevel': menuLevel } };
    return this.http.get<UserMenu[]>(this.apiRefUrl + '/menu/menuLevel', Object.assign({}, params));
  }

  findAllMenuList(): Observable<UserMenu[]> {
    return this.http.get<UserMenu[]>(this.apiRefUrl + '/menu/menuLists');
  }

  deleteMenu(userMenu: UserMenu): Observable<UserMenu[]> {
    return this.http.post<[]>((this.apiRefUrl + '/menu/deleteMenu'), userMenu);
  }

  /*** IDM USER GROUP ***/
  createUserGrp(userGrp: UserGroup): Observable<UserGroup[]> {
    return this.http.post<[]>((this.apiRefUrl + '/userGroup/addUserGroup'), userGrp);
  }

  updateUserGrp(userGrp: UserGroup): Observable<UserGroup[]> {
    return this.http.post<[]>((this.apiRefUrl + '/userGroup/updateUserGroup'), userGrp);
  }

  searchUserGrp(userGrp: UserGroup): Observable<UserGroup[]> {
    return this.http.post<UserGroup[]>((this.apiRefUrl + '/userGroup/searchUserGroup'), userGrp);
  }

  deleteUserGrp(userGrp: UserGroup): Observable<UserGroup[]> {
    return this.http.post<[]>((this.apiRefUrl + '/userGroup/deleteUserGroup'), userGrp);
  }

  findAllUserTypeList(): Observable<UserType[]> {
    return this.http.get<UserType[]>(this.apiRefUrl + '/userGroup/userTypeList');
  }

  findAllUserGroupList(): Observable<UserGroup[]> {
    return this.http.get<UserGroup[]>(this.apiRefUrl + '/userGroup/userGroupList');
  }

  findGroupCodeByCode(groupCode: string): Observable<UserGroup> {
    const params = { params: { 'groupCode': groupCode } };
    return this.http.get<UserGroup>(this.apiRefUrl + '/userGroup/byGroupCode', Object.assign({}, params));
  }

  /*** IDM ROLE ***/

  retrieveUserRoles(): Observable<UserRole[]> {
    return this.http.get<UserRole[]>(this.apiRefUrl + '/user-role');
  }

  retrieveUserRolesIncludePortalType(): Observable<UserRole[]> {
    return this.http.get<UserRole[]>(this.apiRefUrl + '/user-role/portalType');
  }

  createUserRole(userRole: UserRole): Observable<UserRole> {
    this.logger.debug('Create userRole:  [', userRole, ']');
    return this.http.post<UserRole>((this.apiRefUrl + '/user-role/create'), userRole);
  }

  updateUserRole(userRole: UserRole): Observable<UserRole> {
    this.logger.debug('Update userRole:  [', userRole, ']');
    return this.http.post<UserRole>((this.apiRefUrl + '/user-role/update'), userRole);
  }

  searchUserRole(userRole: UserRole): Observable<UserRole[]> {
    this.logger.debug('Search userRole:  [', userRole, ']');
    return this.http.post<UserRole[]>((this.apiRefUrl + '/user-role/search'), userRole);
  }

  searchByUserRoleCd(userRoleCd: string): Observable<UserRole[]> {
    const params = { params: { 'roleCode': userRoleCd } };
    return this.http.get<UserRole[]>(this.apiRefUrl + '/user-role/findByUserRoleCd', Object.assign({}, params));
  }

  deleteUserRole(userRole: UserRole): Observable<boolean> {
    return this.http.post<boolean>((this.apiRefUrl + '/user-role/delete'), userRole);
  }

  /**
    updateUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<UserGroupBranch> {
    this.logger.debug('Update UserGroupBranch:  [', userGroupBranch, ']');
    return this.http.post<UserGroupBranch>((this.apiRefUrl + '/userGroup/usergroupbranch/update'), userGroupBranch);
  }

  deleteUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<boolean> {
    return this.http.post<boolean>((this.apiRefUrl + '/userGroup/usergroupbranch/delete'), userGroupBranch);
  }

  searchUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<UserGroupBranch[]> {
    this.logger.debug('Search UserGroupBranch:  [', userGroupBranch, ']');
    return this.http.post<[]>((this.apiRefUrl + '/userGroup/usergroupbranch/search'), userGroupBranch);
  } 
  */

  /*** IDM USER CONFIG ***/

  findAllUserConfig(): Observable<IdmConfigDto[]> {
    return this.http.get<IdmConfigDto[]>(this.apiRefUrl + '/userConfig/allUserConfig');
  }

  updateUserConfig(userCfg: IdmConfigDto): Observable<IdmConfigDto[]> {
    return this.http.post<[]>((this.apiRefUrl + '/userConfig/updateUserConfig'), userCfg);
  }

  findConfigByConfigCode(configCode: string): Observable<IdmConfigDto> {
    const params = { params: { 'configCode': configCode } };
    return this.http.get<IdmConfigDto>(this.apiRefUrl + '/userConfig/configCode', Object.assign({}, params));
  }

  /*** IDM ROLE MENU ***/
  retrieveRoleMenus(): Observable<RoleMenu[]> {
    return this.http.get<RoleMenu[]>(this.apiRefUrl + '/role-menu');
  }

  deleteRoleMenu(roleMenu: RoleMenu): Observable<[]> {
    return this.http.post<[]>((this.apiRefUrl + '/role-menu/delete'), roleMenu);
  }

  createRoleMenu(roleMenu: RoleMenu): Observable<[]> {
    this.logger.debug('Create RoleMenu:  [', roleMenu, ']');
    return this.http.post<[]>((this.apiRefUrl + '/role-menu/create'), roleMenu);
  }

  updateRoleMenu(roleMenu: RoleMenu): Observable<[]> {
    this.logger.debug('Update RoleMenu:  [', roleMenu, ']');
    return this.http.post<[]>((this.apiRefUrl + '/role-menu/update'), roleMenu);
  }

  searchRoleMenuPaginated(payload: any) {
    //payload.portalAdmin = isPortalAdmin;
    const params = { params: payload };
    return this.http
      .post(
        this.apiRefUrl + '/role-menu/paginated',payload
      );
  }

  findRoleMenuById(id: any): Observable<RoleMenu> {
    const params = { params: { 'id': id } };
    return this.http.get<RoleMenu>(this.apiRefUrl + '/role-menu/roleMenu', Object.assign({}, params));
  }

  /*** IDM USER GROUP ROLE ***/

  retrieveUserGroupRoles(): Observable<UserGroupRole[]> {
    return this.http.get<UserGroupRole[]>(this.apiRefUrl + '/user-group-role');
  }

  deleteUserGroupRole(userGroupRole: UserGroupRole): Observable<[]> {
    return this.http.post<[]>((this.apiRefUrl + '/user-group-role/delete'), userGroupRole);
  }

  createUserGroupRole(userGroupRole: UserGroupRole): Observable<[]> {
    this.logger.debug('Create UserGroupRole:  [', userGroupRole, ']');
    return this.http.post<[]>((this.apiRefUrl + '/user-group-role/create'), userGroupRole);
  }

  searchUserGroupRolePaginated(payload: any, isPortalAdmin: boolean) {
    payload.portalAdmin = isPortalAdmin;
    const params = { params: payload };
    return this.http
      .get(
        this.apiRefUrl + '/user-group-role/paginated',
        Object.assign({}, params)
      );
  }

  updateUserGroupRole(userGroupRole: UserGroupRole): Observable<[]> {
    this.logger.debug('Update UserGroupRole:  [', userGroupRole, ']');
    return this.http.post<[]>((this.apiRefUrl + '/user-group-role/update'), userGroupRole);
  }

  findGroupRoleById(id: any): Observable<UserGroupRole> {
    const params = { params: { 'id': id } };
    return this.http.get<UserGroupRole>(this.apiRefUrl + '/user-group-role/groupRole', Object.assign({}, params));
  }

  /*** IDM USER GROUP BRANCH ***/

  retrieveUserGroupBranch(): Observable<UserGroupBranch[]> {
    return this.http.get<UserGroupBranch[]>(this.apiRefUrl + '/userGroup/usergroupbranch');
  }

  createUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<UserGroupBranch> {
    this.logger.debug('Create UserGroupBranch:  [', userGroupBranch, ']');
    return this.http.post<UserGroupBranch>((this.apiRefUrl + '/userGroup/usergroupbranch/create'), userGroupBranch);
  }

  updateUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<UserGroupBranch> {
    this.logger.debug('Update UserGroupBranch:  [', userGroupBranch, ']');
    return this.http.post<UserGroupBranch>((this.apiRefUrl + '/userGroup/usergroupbranch/update'), userGroupBranch);
  }

  deleteUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<boolean> {
    return this.http.post<boolean>((this.apiRefUrl + '/userGroup/usergroupbranch/delete'), userGroupBranch);
  }

  searchUserGroupBranch(userGroupBranch: UserGroupBranch): Observable<UserGroupBranch[]> {
    this.logger.debug('Search UserGroupBranch:  [', userGroupBranch, ']');
    return this.http.post<[]>((this.apiRefUrl + '/userGroup/usergroupbranch/search'), userGroupBranch);
  }

  /*** IDM OAUTH CLIENT DETAILS ***/

  retrieveOauthClientDetails(): Observable<OauthClientDetails[]> {
    return this.http.get<OauthClientDetails[]>(this.apiRefUrl + '/oauth/oauthClientDetails');
  }

  createOauthClientDetails(ocds: OauthClientDetails[]): Observable<[]> {
    this.logger.debug('Create OauthClientDetails:  [', ocds, ']');
    return this.http.post<[]>((this.apiRefUrl + '/oauth/oauthClientDetails/create'), ocds);
  }

  updateOauthClientDetails(ocds: OauthClientDetails[]): Observable<[]> {
    this.logger.debug('Update OauthClientDetails:  [', ocds, ']');
    return this.http.post<[]>((this.apiRefUrl + '/oauth/oauthClientDetails/update'), ocds);
  }

  deleteOauthClientDetails(ocd: OauthClientDetails): Observable<boolean> {
    return this.http.post<boolean>((this.apiRefUrl + '/oauth/oauthClientDetails/delete'), ocd);
  }

}
