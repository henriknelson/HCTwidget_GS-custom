package com.microntek;

import android.content.Context;
import android.content.res.Resources;
import com.microntek.hctwidget.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarLunar {
    private static final int[] LUNAR_INFO_TABLE = new int[]{19416, 19168, 42352, 21717, 53856, 55632, 91476, 22176, 39632, 21970, 19168, 42422, 42192, 53840, 119381, 46400, 54944, 44450, 38320, 84343, 18800, 42160, 46261, 27216, 27968, 109396, 11104, 38256, 21234, 18800, 25958, 54432, 59984, 28309, 23248, 11104, 100067, 37600, 116951, 51536, 54432, 120998, 46416, 22176, 107956, 9680, 37584, 53938, 43344, 46423, 27808, 46416, 86869, 19872, 42448, 83315, 21200, 43432, 59728, 27296, 44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 103846, 38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 43344, 46240, 47780, 44368, 21977, 19360, 42416, 86390, 21168, 43312, 31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 23200, 30371, 38608, 19415, 19152, 42192, 118966, 53840, 54560, 56645, 46496, 22224, 21938, 18864, 42359, 42160, 43600, 111189, 27936, 44448, 19299, 37759, 18936, 18800, 25776, 26790, 59999, 27424, 42692, 43759, 37600, 53987, 51552, 54615, 54432, 55888, 23893, 22176, 42704, 21972, 21200, 43448, 43344, 46240, 46758, 44368, 21920, 43940, 42416, 21168, 45683, 26928, 29495, 27296, 44368, 19285, 19311, 42352, 21732, 53856, 59752, 54560, 55968, 27302, 22239, 19168, 43476, 42192, 53584, 62034, 54560};
    private static CalendarLunar mCalendarLunar = null;
    private static char[][] mPrincipleTermMaps = new char[][]{new char[]{21, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 20, 20, 20, 20, 20, 19, 20, 20, 20, 19, 19, 20}, new char[]{20, 19, 19, 20, 20, 19, 19, 19, 19, 19, 19, 19, 19, 18, 19, 19, 19, 18, 18, 19, 19, 18, 18, 18, 18, 18, 18, 18}, new char[]{21, 21, 21, 22, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 21, 20, 20, 20, 20, 19, 20, 20, 20, 20}, new char[]{20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 21, 20, 20, 20, 20, 19, 20, 20, 20, 19, 19, 20, 20, 19, 19, 19, 20, 20}, new char[]{21, 22, 22, 22, 21, 21, 22, 22, 21, 21, 21, 22, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 20, 20, 20, 21, 21}, new char[]{22, 22, 22, 22, 21, 22, 22, 22, 21, 21, 22, 22, 21, 21, 21, 22, 21, 21, 21, 21, 20, 21, 21, 21, 20, 20, 21, 21, 21}, new char[]{23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 22, 22, 22, 22, 23}, new char[]{23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 23}, new char[]{23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 23}, new char[]{24, 24, 24, 24, 23, 24, 24, 24, 23, 23, 24, 24, 23, 23, 23, 24, 23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 23}, new char[]{23, 23, 23, 23, 22, 23, 23, 23, 22, 22, 23, 23, 22, 22, 22, 23, 22, 22, 22, 22, 21, 22, 22, 22, 21, 21, 22, 22, 22}, new char[]{22, 22, 23, 23, 22, 22, 22, 23, 22, 22, 22, 22, 21, 22, 22, 22, 21, 21, 22, 22, 21, 21, 21, 22, 21, 21, 21, 21, 22}};
    private static char[][] mPrincipleTermYears = new char[][]{new char[]{13, '-', 'Q', 'q', 149, 185, 201}, new char[]{21, '9', ']', '}', 161, 193, 201}, new char[]{21, '8', 'X', 'x', 152, 188, 200, 201}, new char[]{21, '1', 'Q', 't', 144, 176, 200, 201}, new char[]{17, '1', 'M', 'p', 140, 168, 200, 201}, new char[]{28, '<', 'X', 't', 148, 180, 200, 201}, new char[]{25, '5', 'T', 'p', 144, 172, 200, 201}, new char[]{29, '9', 'Y', 'x', 148, 180, 200, 201}, new char[]{17, '-', 'I', 'l', 140, 168, 200, 201}, new char[]{28, '<', '\\', '|', 160, 192, 200, 201}, new char[]{16, ',', 'P', 'p', 148, 180, 200, 201}, new char[]{17, '5', 'X', 'x', 156, 188, 200, 201}};
    private static char[][] mSectionalTermMaps = new char[][]{new char[]{7, 6, 6, 6, 6, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 4, 5, 5}, new char[]{5, 4, 5, 5, 5, 4, 4, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 3, 4, 4, 4, 3, 3, 4, 4, 3, 3, 3}, new char[]{6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 5}, new char[]{5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 4, 4, 5, 5, 4, 4, 4, 5, 4, 4, 4, 4, 5}, new char[]{6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 5}, new char[]{6, 6, 7, 7, 6, 6, 6, 7, 6, 6, 6, 6, 5, 6, 6, 6, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 4, 5, 5, 5, 5}, new char[]{7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6, 7, 7, 6, 6, 6, 7, 7}, new char[]{8, 8, 8, 9, 8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6, 7, 7, 7}, new char[]{8, 8, 8, 9, 8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 7}, new char[]{9, 9, 9, 9, 8, 9, 9, 9, 8, 8, 9, 9, 8, 8, 8, 9, 8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 8}, new char[]{8, 8, 8, 8, 7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6, 7, 7, 7}, new char[]{7, 8, 8, 8, 7, 7, 8, 8, 7, 7, 7, 8, 7, 7, 7, 7, 6, 7, 7, 7, 6, 6, 7, 7, 6, 6, 6, 7, 7}};
    private static char[][] mSectionalTermYears = new char[][]{new char[]{13, '1', 'U', 'u', 149, 185, 201, 250, 250}, new char[]{13, '-', 'Q', 'u', 149, 185, 201, 250, 250}, new char[]{13, '0', 'T', 'p', 148, 184, 200, 201, 250}, new char[]{13, '-', 'L', 'l', 140, 172, 200, 201, 250}, new char[]{13, ',', 'H', 'h', 132, 168, 200, 201, 250}, new char[]{5, '!', 'D', '`', '|', 152, 188, 200, 201}, new char[]{29, '9', 'U', 'x', 148, 176, 200, 201, 250}, new char[]{13, '0', 'L', 'h', 132, 168, 196, 200, 201}, new char[]{25, '<', 'X', 'x', 148, 184, 200, 201, 250}, new char[]{16, ',', 'L', 'l', 144, 172, 200, 201, 250}, new char[]{28, '<', '\\', '|', 160, 192, 200, 201, 250}, new char[]{17, '5', 'U', '|', 156, 188, 200, 201, 250}};
    private long mBaseDateTimeInMillis;
    private LunarCache mCache = new LunarCache();
    private Calendar mCalendar = Calendar.getInstance();
    private int mCalendarDay;
    private int mCalendarMonth;
    private int mCalendarYear;
    private int mDay;
    private boolean mIsLeap;
    private String mLeaplabel;
    private String[] mLunarAnimal;
    private String[] mLunarDizhi;
    private String mLunarNumbers;
    private String[] mLunarTiangan;
    private String mLunarday;
    private int mMonth;
    private String mMonthlabel;
    private int mPrincipleTerm;
    private int mSectionalTerm;
    private String[] mSolarTerms;
    private HashMap<Integer, String> mSolarfestivalMap = new HashMap();
    private int mYear;
    private HashMap<Integer, String> mlunarFestivalMap = new HashMap();

    private static class LunarCache {
        boolean cacheEnable;
        int dateOffset;
        int lunarDayCountOfYear;
        int lunarDayOffsetYear;
        int lunarYear;

        LunarCache() {
            this.cacheEnable = false;
        }
    }

    public static CalendarLunar getInstance(Context context) {
        if (mCalendarLunar == null) {
            mCalendarLunar = new CalendarLunar(context);
        }
        return mCalendarLunar;
    }

    private CalendarLunar(Context context) {
        this.mCalendar.clear();
        this.mCalendar.set(1900, 0, 31, 0, 0);
        this.mBaseDateTimeInMillis = this.mCalendar.getTimeInMillis();
        Resources res = context.getResources();
        this.mSolarTerms = res.getStringArray(R.array.solar_terms);
        this.mLunarAnimal = res.getStringArray(R.array.lunar_animal);
        this.mLunarTiangan = res.getStringArray(R.array.lunar_Tiangan);
        this.mLunarDizhi = res.getStringArray(R.array.lunar_Dizhi);
        this.mLunarday = res.getString(R.string.lunar_day);
        this.mLunarNumbers = res.getString(R.string.lunar_numbers);
        this.mLeaplabel = res.getString(R.string.leap_label);
        this.mMonthlabel = res.getString(R.string.month_label);
        getFestival(this.mSolarfestivalMap, res.getStringArray(R.array.calendar_festival));
        getFestival(this.mlunarFestivalMap, res.getStringArray(R.array.lunar_festival));
    }

    public void setDateInfo(int year, int month, int day) {
        calculationLunar(year, month, day);
        int tLunaryear = this.mCache.lunarYear;
        int tLeapMonth = getLeapMonth(tLunaryear);
        this.mIsLeap = false;
        boolean tLeap = false;
        int tOffset = this.mCache.lunarDayOffsetYear;
        int daysOfMonth = 0;
        int tMonth = 1;
        while (tMonth < 13 && tOffset > 0) {
            if (tLeapMonth <= 0 || tMonth != tLeapMonth + 1 || tLeap == false) {
                daysOfMonth = monthDays(tLunaryear, tMonth);
            } else {
                tMonth--;
                tLeap = true;
                this.mIsLeap = true;
                daysOfMonth = getLeapMonthDays(tLunaryear);
            }
            tOffset -= daysOfMonth;
            if (tLeap && tMonth == tLeapMonth + 1) {
                tLeap = false;
                this.mIsLeap = false;
            }
            tMonth++;
        }
        if (tOffset == 0 && tLeapMonth > 0 && tMonth == tLeapMonth + 1) {
            if (tLeap) {
                this.mIsLeap = false;
            } else {
                this.mIsLeap = true;
                tMonth--;
            }
        }
        if (tOffset <= 0) {
            tOffset += daysOfMonth;
            tMonth--;
        } else if (tMonth > 12) {
            tMonth = 1;
        }
        this.mYear = tLunaryear;
        this.mMonth = tMonth;
        this.mDay = tOffset;
        this.mCalendarYear = this.mCalendar.get(Calendar.YEAR);
        this.mCalendarMonth = this.mCalendar.get(Calendar.MONTH);
        this.mCalendarDay = this.mCalendar.get(Calendar.DAY_OF_MONTH);
        this.mSectionalTerm = calculatesTerm(this.mCalendarYear, this.mCalendarMonth + 1);
        this.mPrincipleTerm = calculatepTerm(this.mCalendarYear, this.mCalendarMonth + 1);
    }

    private void calculationLunar(int year, int month, int day) {
        this.mCalendar.clear();
        this.mCalendar.set(year, month, day, 1, 0);
        int dateOffset = (int) ((this.mCalendar.getTimeInMillis() - this.mBaseDateTimeInMillis) / 86400000);
        if (this.mCache.cacheEnable) {
            int lunarDayOffsetYear = (this.mCache.lunarDayOffsetYear + dateOffset) - this.mCache.dateOffset;
            if (lunarDayOffsetYear < 0 || lunarDayOffsetYear >= this.mCache.lunarDayCountOfYear) {
                createCache(dateOffset);
                return;
            }
            this.mCache.lunarDayOffsetYear = lunarDayOffsetYear;
            this.mCache.dateOffset = dateOffset;
            return;
        }
        createCache(dateOffset);
    }

    private void createCache(int dateOffset) {
        int offset = dateOffset;
        int daysOfYear = 0;
        int iYear = 1900;
        while (iYear < 2050 && offset > 0) {
            daysOfYear = getLunarYearDayCount(iYear);
            offset -= daysOfYear;
            iYear++;
        }
        if (offset <= 0) {
            offset += daysOfYear;
            iYear--;
        }
        this.mCache.lunarYear = iYear;
        this.mCache.lunarDayOffsetYear = offset + 1;
        this.mCache.lunarDayCountOfYear = daysOfYear;
        this.mCache.dateOffset = dateOffset;
        this.mCache.cacheEnable = true;
    }

    private void getFestival(Map<Integer, String> festivalMap, String[] festivalArray) {
        for (String i : festivalArray) {
            int inx = i.indexOf(44);
            festivalMap.put(Integer.valueOf(Integer.parseInt(i.substring(0, inx))), i.substring(inx + 1));
        }
    }

    private int getLunarYearDayCount(int year) {
        int sum = 348;
        for (int i = 32768; i > 8; i >>= 1) {
            if ((LUNAR_INFO_TABLE[year - 1900] & i) != 0) {
                sum++;
            }
        }
        return getLeapMonthDays(year) + sum;
    }

    private int getLeapMonth(int year) {
        return LUNAR_INFO_TABLE[year - 1900] & 15;
    }

    private int getLeapMonthDays(int year) {
        if (getLeapMonth(year) == 0) {
            return 0;
        }
        if ((LUNAR_INFO_TABLE[year - 1900] & 65536) != 0) {
            return 30;
        }
        return 29;
    }

    private int monthDays(int year, int month) {
        if ((LUNAR_INFO_TABLE[year - 1900] & (65536 >> month)) == 0) {
            return 29;
        }
        return 30;
    }

    public int calculatesTerm(int y, int m) {
        int index = 0;
        int ry = y - 1900;
        while (ry >= mSectionalTermYears[m - 1][index]) {
            index++;
        }
        int term = mSectionalTermMaps[m - 1][(index * 4) + (ry % 4)];
        if (ry == 'y' && m == 4) {
            term = 5;
        }
        if (ry == 132 && m == 4) {
            term = 5;
        }
        if (ry == 194 && m == 6) {
            return 6;
        }
        return term;
    }

    public int calculatepTerm(int y, int m) {
        int index = 0;
        int ry = y - 1900;
        while (ry >= mPrincipleTermYears[m - 1][index]) {
            index++;
        }
        int term = mPrincipleTermMaps[m - 1][(index * 4) + (ry % 4)];
        if (ry == 171 && m == 3) {
            term = 21;
        }
        if (ry == 181 && m == 5) {
            return 21;
        }
        return term;
    }

    public String getSolarTermString() {
        int month = this.mCalendarMonth;
        String solarTerms = "";
        if (this.mCalendarDay == this.mSectionalTerm) {
            return this.mSolarTerms[month * 2];
        }
        if (this.mCalendarDay == this.mPrincipleTerm) {
            return this.mSolarTerms[(month * 2) + 1];
        }
        return solarTerms;
    }

    public String getSolarfestivalString() {
        String v = (String) this.mSolarfestivalMap.get(Integer.valueOf(this.mCalendarDay + ((this.mCalendarMonth + 1) * 100)));
        return v != null ? v : "";
    }

    public String getLunarFestivalString() {
        String v = (String) this.mlunarFestivalMap.get(Integer.valueOf(this.mDay + (this.mMonth * 100)));
        return v != null ? v : "";
    }

    public String getLunarDayString() {
        int d = this.mDay > 0 ? this.mDay - 1 : this.mDay;
        String s = "";
        if (this.mDay == 10) {
            s = s + this.mLunarday.charAt(0);
        } else {
            s = s + this.mLunarday.charAt(this.mDay / 10);
        }
        return s + this.mLunarNumbers.charAt(d % 10);
    }

    public String getLunarMonthString() {
        String s = this.mIsLeap ? this.mLeaplabel : "";
        if (this.mMonth >= 10) {
            s = s + this.mLunarday.charAt(1);
        }
        if (this.mMonth != 10) {
            s = s + this.mLunarNumbers.charAt((this.mMonth % 10) - 1);
        }
        return s + this.mMonthlabel;
    }

    public String getDateInfoString(int year, int month, int day) {
        setDateInfo(year, month, day);
        return getDateInfo();
    }

    public String getDateInfo() {
        String str = "";
        str = getSolarfestivalString();
        if (str.isEmpty()) {
            str = getLunarFestivalString();
            if (str.isEmpty()) {
                str = getSolarTermString();
            }
        }
        if (str.isEmpty()) {
            return getLunarMonthString() + getLunarDayString();
        }
        return getLunarMonthString() + getLunarDayString() + "  " + str;
    }
}
