import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { AuthService } from './auth.service';
import { AuthTypeConstants } from '../../constants';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivateChild {
    constructor(private router: Router, private authService: AuthService) { }

    canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.authService.currentUserValue;
        if (currentUser) {
            const userRoles: Array<string> = currentUser.roles.map(r => r.roleCode);
            const userMenus: Array<string> = currentUser.menus ? currentUser.menus.map(r => r.menuCode) : null;

            if (route.data && route.data.authorization) {
                const roleType = route.data.authorization.roletype || {};

                if ((roleType && roleType === AuthTypeConstants.PERMIT_ALL)
                    || (roleType && roleType === AuthTypeConstants.ANONYMOUS)) {
                    return true;
                }

                if (roleType && roleType === AuthTypeConstants.AUTHENTICATED) {
                    const wantedRoles: Array<string> = route.data.authorization.roles || [];
                    const menuCode: string = route.data.authorization.menuCode || null;

                    if(userMenus) {
                        if (!userMenus.length && menuCode != null) {
                            throw new Error('Permission Required');
                        } else {
                            if (userMenus.indexOf(menuCode) >= 0) {
                                return true;
                            } else {
                                for (const role of wantedRoles) {
                                    if (userRoles.indexOf(role) >= 0) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    this.router.navigate(['/error']);
                    return false;
                }
            }
            return true;
        } else {
            // not logged in so redirect to login page with the return url
            this.router.navigate(['login']);
            return false;
        }
    }
}
