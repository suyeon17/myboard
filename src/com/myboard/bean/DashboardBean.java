package com.myboard.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class DashboardBean implements Serializable {
	private static final long serialVersionUID = -627664203489195833L;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
	private static final int WEEKS_OFFSET_DEFAULT = -1;
	private static final int WEEKS_SHOWN_DEFAULT = 6;
	private final HashMap<Integer, Week> weeksMap = new HashMap<Integer,Week>();
	private final HashMap<Long, Day> daysMap = new HashMap<Long,Day>();
	private int weeksOffset = WEEKS_OFFSET_DEFAULT;
	private int weeksShown = WEEKS_SHOWN_DEFAULT;
	private final Calendar calendarToday;
	private final Calendar calendarBase;
	private Day selectedDay;
	private boolean renderPopup = false;
	
	public DashboardBean() {
		calendarToday = Calendar.getInstance(); //get the calendar for today, shift time to 0
		calendarToday.set(Calendar.HOUR_OF_DAY, 0);
		calendarToday.set(Calendar.MINUTE, 0);
		calendarToday.set(Calendar.SECOND, 0);
		calendarToday.set(Calendar.MILLISECOND, 0);
		
		calendarBase = (Calendar)calendarToday.clone(); //copy today, shift to offset
		calendarBase.add(Calendar.WEEK_OF_YEAR, weeksOffset);
		
		initWeekMap();
		
		//sample events added on init
		daysMap.get(calendarToday.getTimeInMillis()).addEvent(0, new CalendarEvent(CalendarEvent.STATUS_ASSIGNMENTS_DUE, "CIS485", "Assignment 5 Due", "http://www.google.com"));
		daysMap.get(calendarToday.getTimeInMillis()).addEvent(1, new CalendarEvent(CalendarEvent.STATUS_NOTIFICATION_AVAILABLE, "CIS485", "No Class Today", "http://www.google.com"));

	}
	//popup data updated via ajax call
	public void ajaxListener(String id) {
		selectedDay = daysMap.get(Long.parseLong(id));
		renderPopup = true;
	}
	//false until ajax listener is called for first time
	public boolean getRenderPopup() { return renderPopup; }
	
	//fill weeks map with weeks in data range: (first week): today - weeksOffset(in weeks) to (last week): first week + weeksShown 
	public void initWeekMap() {
		Calendar calendar = (Calendar)calendarBase.clone();
		for (int i = weeksOffset; i < weeksOffset+weeksShown; i++) {
			Week week = new Week(calendar,i);
			addWeek(week);
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
		}
	}
	public Day getSelectedDay() { return selectedDay; }
	
	//add a week into the week map, also adds the days of the week to the days map
	public void addWeek(Week week) {
		weeksMap.put(week.offset, week);
		week.putDays(daysMap);
	}
	
	public void setWeeksOffset(int weeksOffset) { this.weeksOffset = weeksOffset; }
	public int getWeeksOffset() { return weeksOffset; }
	
	public List<Week> getWeeks() {
		ArrayList<Week> list = new ArrayList<Week>();
		for (int i = weeksOffset; i < weeksOffset+weeksShown; i++) list.add(weeksMap.get(i));
		return list;
	}

	public class Week {
		private final Day[] days = new Day[7];
		private final int offset;
		public Week(Calendar calendar, int offset) {
			Calendar cal = (Calendar) calendar.clone();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			for (int i = 0; i < 7; i++) {
				days[i] = new Day(cal);
				cal.add(Calendar.DAY_OF_YEAR, 1);
			}
			this.offset = offset;
		}
		public int getOffset() { return offset; }
		public void putDays(HashMap<Long,Day> daysMap) { for (int i = 0; i < 7; i++) daysMap.put(days[i].getDateMillis(), days[i]); }
		public Day getDay(int i) { return days[i]; }
		public List<Day> getDays() { return Arrays.asList(days); }
	}
	public class Day {
		private HashMap<Integer,CalendarEventManager> eventManagers = new HashMap<Integer, CalendarEventManager>();
		private final Calendar calendarDay;
		private final boolean today;
		public Day(Calendar calendar) {
			calendarDay = (Calendar)calendar.clone();
			today = calendarDay.equals(calendarToday);
			eventManagers.put(CalendarEventManager.ID_ASSIGNMENTS,new CalendarEventManager("Assignments:", "&#xf016;"));
			eventManagers.put(CalendarEventManager.ID_NOTIFICATIONS,new CalendarEventManager("Notifications:", "&#xf0a1;"));
		}
		public void addEvent(int eventManagerId, CalendarEvent event) { eventManagers.get(eventManagerId).addEvent(event); }
		public List<CalendarEventManager> getEventManagers() { return new ArrayList<CalendarEventManager>(eventManagers.values()); }
		public String getMonthString() { return calendarDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()); }
		public String getFullDateString() { return sdf.format(calendarDay.getTime()); }
		public int getDay() { return calendarDay.get(Calendar.DAY_OF_MONTH); }
		public long getDateMillis() { return calendarDay.getTimeInMillis(); }
		public int getNumEventTypes() { return eventManagers.size(); }
		public boolean isToday() { return today; }
		public String getTooltip() {
			String tt = "";
			for (CalendarEventManager cem : getEventManagers())
				if (cem.hasEvents()) for (CalendarEvent event : cem.getEvents()) tt += event.getString() + "<br />";
			if (tt.length() > 0) return tt;
			return "no events for " + (calendarDay.get(Calendar.MONTH)+1) + "/" + calendarDay.get(Calendar.DAY_OF_MONTH);
		}
	}
	public static class CalendarEvent {
		public static final int STATUS_NONE = 0;
		public static final int STATUS_ASSIGNMENTS_DUE = 2;
		public static final int STATUS_ASSIGNMENTS_OVERDUE = 3;
		public static final int STATUS_NOTIFICATION_AVAILABLE = 1;
		private final int status;
		private final String title,description,url;
		public CalendarEvent(int status, String title, String description, String url) {
			this.status = status;
			this.title = title;
			this.description = description;
			this.url = url;
		}
		public int getStatus() { return status; }
		public String getTitle() { return title; }
		public String getDescription() { return description; }
		public String getUrl() { return url; }
		public String getString() { return title + ": " + description; }
		/*
		 *	current Dashboard format shows "title: description" linking to url
		 *	example "CIS485: Assignment 5 due at 11:59:59pm" <- links to assignment 5 on the cis485 page
		 */
	}
	public static class CalendarEventManager {
		public static final int ID_ASSIGNMENTS = 0;
		public static final int ID_NOTIFICATIONS = 1;
		private final ArrayList<CalendarEvent> events = new ArrayList<CalendarEvent>();
		private final String headerText,icon;
		private int highestStatus = 0;
		private static final String[] STATUS_COLOR_MAP = new String[] { "LIGHTGRAY","YELLOW","ORANGE","RED" };
		public CalendarEventManager(String headerText, String icon) { this.headerText = headerText; this.icon = icon; }
		public void addEvent(CalendarEvent event) {
			events.add(event);
			if (event.getStatus() > highestStatus) highestStatus = event.getStatus();
		}
		public int getHighestStatus() { return highestStatus; }
		public String getHighestStatusString() { return STATUS_COLOR_MAP[highestStatus]; }
		public String getIcon() { return icon; }
		public String getHeaderText() { return headerText; }
		public boolean hasEvents() { return events.size() > 0; }
		public List<CalendarEvent> getEvents() { return events; }
		/*
		 * 	current Dashboard format shows "headerText:\n\tevents"
		 * 	example "	Assignments: <- large font
		 * 					"CIS485: Assignment 5 due at 11:59:59pm" <- links to assignment 5 on the cis485 page
		 */
	}
}
