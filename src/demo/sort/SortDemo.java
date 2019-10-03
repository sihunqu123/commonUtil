package demo.sort;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import util.commonUtil.ComLogUtil;

public class SortDemo {

	public static void main(String[] args) {
		dd();
	}

	public static void dd() {
		Set<String> treeSetStr = new TreeSet<String>();
		treeSetStr.add("18");
		treeSetStr.add("4");
		treeSetStr.add("2");
		treeSetStr.add("16");
		treeSetStr.add("18");
		treeSetStr.add("23");
		System.out.println(ComLogUtil.objToFormatString(treeSetStr));

		Set<Integer> treeSetInt = new TreeSet<Integer>();
		treeSetInt.add(18);
		treeSetInt.add(4);
		treeSetInt.add(2);
		treeSetInt.add(16);
		treeSetInt.add(18);
		treeSetInt.add(23);
		System.out.println(ComLogUtil.objToFormatString(treeSetInt));


		TreeMap<Integer,String> treeMap = new TreeMap<Integer, String>();
		treeMap.put(8, "value555of");
		treeMap.put(2, "value23242322of");
		treeMap.put(111, "value00of");
		treeMap.put(4, "value4of");
		treeMap.put(20, "value2022addof");
		System.out.println(ComLogUtil.objToFormatString(treeMap));
	}
}