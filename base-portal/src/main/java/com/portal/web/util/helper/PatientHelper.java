/**
 * 
 */
package com.portal.web.util.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.idm.sdk.model.UserGroupBranch;
import com.portal.core.AbstractController;
import com.util.BaseUtil;

/**
 * @author mary.jane
 *
 */
@Component
public class PatientHelper extends AbstractController {

	public List<Integer> findClinicIdByState(String stateCd) {
		List<Integer> clinicIdLst = new ArrayList<>();
		UserGroupBranch userGroupBranch = new UserGroupBranch();
		userGroupBranch.setStateCd(stateCd);
		List<UserGroupBranch> usGroupBranchLst = getIdmService().searchUserGroupBranch(userGroupBranch);
		if (!BaseUtil.isListNull(usGroupBranchLst)) {
			usGroupBranchLst.forEach(usr -> {
				if (!BaseUtil.isObjNull(usr) && !BaseUtil.isObjNull(usr.getBranchId())) {
					clinicIdLst.add(usr.getBranchId());
				}
			});
		}
		return clinicIdLst;
	}
	
}
