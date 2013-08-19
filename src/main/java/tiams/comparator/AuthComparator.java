package tiams.comparator;

import java.util.Comparator;

import tiams.model.Auth;

/**
 * Auth排序
 * 
 * @author nonkr
 * 
 */
public class AuthComparator implements Comparator<Auth> {

	public int compare(Auth o1, Auth o2) {
		int i1 = o1.getSeq() != null ? o1.getSeq().intValue() : -1;
		int i2 = o2.getSeq() != null ? o2.getSeq().intValue() : -1;
		return i1 - i2;
	}

}
