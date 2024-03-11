package com.epam.rd.autocode.set;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Project {
	private static List<Role> roleList;
	private static List<Member> teamList;

	public static List<Role> getRoles() {
		return roleList;
	}
	
	private static class Entry {
		private Pair<Level,Skill> entryPair;
		@Override
		public boolean equals(Object o) {
			if (o==null){throw new NullPointerException();}
			Pair<Level, Skill> castedObject = (Pair<Level, Skill>) o;
			if (this.entryPair.getLeft() == castedObject.getLeft() && this.entryPair.getRight() == castedObject.getRight()){
				return true;
			}
			return false;
		}
		@Override
		public int hashCode() {
			int hashLeft = Objects.hash(entryPair.getLeft());
			int hashRight = Objects.hash(entryPair.getRight());
			return hashLeft+hashRight;
		}
	}
	public Project(Role... roles) {
		roleList = List.of(roles);
	}

	public int getConformity(Set<Member> team) {

		teamList = new ArrayList<>(team);
		Collections.reverse(teamList);

		List<Pair<Level, Skill>> projectEntries = new ArrayList<>();
		int originalSize = 0;
		List<Pair<Level, Skill>> teamEntries = new ArrayList<>();

		Pair<Level, Skill> entryPair;

		for (int i=0; i< roleList.size(); i++) {
			Set<Skill> skills = roleList.get(i).getSkills();
			Level level = roleList.get(i).getLevel();
			for (Skill sk : skills){
				entryPair = Pair.of(level,sk);
				projectEntries.add(entryPair);
			}
		}
		originalSize = projectEntries.size();

		for (int i=0; i< teamList.size(); i++) {
			Set<Skill> skills = teamList.get(i).getSkills();
			Level level = teamList.get(i).getLevel();
			for (Skill sk : skills){
				entryPair = Pair.of(level,sk);
				new Entry().entryPair = entryPair;
				teamEntries.add(entryPair);
			}
		}
		System.out.println(projectEntries+" Project");
		System.out.println(teamEntries +" Team");

		List<Pair<Level, Skill>> removed = new ArrayList<>();
		for (Iterator<Pair<Level, Skill>> projectIterator = projectEntries.iterator();projectIterator.hasNext();){
			Pair<Level, Skill> projNext = projectIterator.next();
			for (Iterator<Pair<Level, Skill>> teamIterator = teamEntries.iterator(); teamIterator.hasNext();){
				Pair<Level, Skill> teamNext = teamIterator.next();
				if (teamNext.equals(projNext) && teamNext.hashCode() == projNext.hashCode() ){
					removed.add(teamNext);
					teamIterator.remove();
					//projectIterator.remove();
				}
			}
		}
		HashSet<Pair<Level, Skill>> pairs = new HashSet<>(removed);
		for (Iterator<Pair<Level, Skill>> pairIterator = pairs.iterator();pairIterator.hasNext();){
			Pair<Level, Skill> nextPair = pairIterator.next();
			for (Iterator<Pair<Level, Skill>> projectIterator = projectEntries.iterator();projectIterator.hasNext();){
				Pair<Level, Skill> nextProject = projectIterator.next();

				if (nextPair.equals(nextProject) && nextPair.hashCode() == nextProject.hashCode() && projectIterator.hasNext()){
					projectIterator.remove();
				}else if (nextPair.equals(nextProject) && nextPair.hashCode() == nextProject.hashCode() && projectEntries.size()==1){
					projectIterator.remove();
				}


			}
		}

		//projectEntries.removeAll(pairs);
		//teamEntries.removeAll(removed);
		System.out.println(pairs+" Párok");
		System.out.println(projectEntries+" Project utána");
		System.out.println(teamEntries+" Team utána");

		System.out.println(projectEntries.size() +" subtracted");
		System.out.println(originalSize + " orig");
		int subtracted = originalSize - projectEntries.size();
		//double multiplied = subtracted * 100.0;
		int multiplied = subtracted * 100;
		int toReturn = multiplied / originalSize;
		//String string = i.toString();
		//int toReturn = Double.valueOf(string).intValue();
//		System.out.println(toReturn);
		return toReturn;
	}
	
}
