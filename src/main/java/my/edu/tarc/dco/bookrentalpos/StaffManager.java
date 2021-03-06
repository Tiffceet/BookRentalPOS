package my.edu.tarc.dco.bookrentalpos;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to load Staff data for the POS system
 *
 * @author Looz
 * @version 1.0
 */
public class StaffManager extends Manager<Staff> {

    private Staff[] staffList;

    /**
     * This is used to reference a staff that is logon through login()
     *
     * @see StaffManager#login(String, String)
     */
    private Staff logOnStaff;
    private int staffCount;
    private DBManager db;
    private final int ARRAY_SIZE = 100;

    public StaffManager(DBManager db) {
        this.db = db;
        logOnStaff = null;
        staffList = new Staff[ARRAY_SIZE];
        reload();

        // Code to add default 'root' user to database if not exist
        Staff rootStaff = this.getByName("root");
        if (rootStaff == null) {
            rootStaff = new Staff("root", "");
            rootStaff.setAdminStatus(true);
            this.add(rootStaff);
            staffList[staffCount++] = rootStaff;
            reload();
        }
    }

    /**
     * Reload the staff database
     */
    @Override
    public void reload() {
        staffCount = 0;
        String sql = "SELECT * FROM staff;";
        try {
            java.sql.ResultSet rs = db.resultQuery(sql);
            while (rs.next()) {
                Staff s = new Staff(rs.getInt("id"), rs.getString("date"), rs.getString("name"), rs.getString("password"));
                if (s.getName().equals("root")) {
                    s.setAdminStatus(true);
                }
                staffList[staffCount++] = s;
            }
        } catch (java.sql.SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    /**
     * Used to check if the provided username and password matches database
     *
     * @param usrName username of the Staff
     * @param pw      password of the staff in plain text
     * @return staff ID if it matches, returns -1 if nothing matches
     */
    public int login(String usrName, String pw) {
        for (int i = 0; i < staffCount; i++) {
            if (staffList[i].getName().equals(usrName) && staffList[i].getPW().equals(CustomUtil.md5Hash(pw))) {
                logOnStaff = staffList[i];
                return staffList[i].getId();
            }
        }
        return -1;
    }

    /**
     * Get staff object reference by id
     *
     * @param staffID StaffID
     * @return Staff object reference of specified ID, return null if StaffID
     * was not found
     */
    @Override
    public Staff getById(int staffID) {
        for (int i = 0; i < staffCount; i++) {
            if (staffList[i].getId() == staffID) {
                return staffList[i];
            }
        }
        return null;
    }

    /**
     * Get staff object reference by name
     *
     * @param name staff username
     * @return Staff object reference of specified ID, return null if StaffID
     * was not found
     */
    @Override
    public Staff getByName(String name) {
        for (int i = 0; i < staffCount; i++) {
            if (staffList[i].getName().equals(name)) {
                return staffList[i];
            }
        }
        return null;
    }

    /**
     * This function return a copy of the staff list loaded from the database
     *
     * @return an array of Staff, use StaffManager.getStaffCount() to get the number of entry
     * @see #getStaffCount()
     */
    @Override
    public Staff[] getCache() {
        return this.staffList.clone();
    }

    /**
     * @return staff that is log on using login() function
     */
    public Staff getLogOnStaff() {
        return this.logOnStaff;
    }

    /**
     * Register a new staff into database
     *
     * @param stf Staff object without ID
     * @return true if registration was successful, false if same staff name
     * existed
     * @see Staff#Staff(java.lang.String, java.lang.String)
     */
    @Override
    public boolean add(Staff stf) {

        String sql = String.format("INSERT INTO staff(name, password) VALUES(\"%s\", \"%s\")", stf.getName(), stf.getPW());
        // updateQuery() return rows affected from the sql query
        if (db.updateQuery(sql) == 1) {
            try {
                // id and date is generated by sqlite, i need to make a copy of it and store it in my preloaded database
                // this query basically get the latest Staff entry inserted into database
                ResultSet rs = db.resultQuery("SELECT id, date FROM staff WHERE id = (SELECT seq FROM sqlite_sequence WHERE name='staff')");
                stf.setID(rs.getInt("id"));
                stf.setDateCreated(rs.getString("date"));

                // store in my preloaded database
                staffList[staffCount++] = stf;
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update staff information to database
     *
     * @param stf Staff object, expecting Staff object reference instead of new
     *            Staff object
     * @return true if staff info was updated successfully
     */
    @Override
    public boolean update(Staff stf) {
        if (stf.getId() == 0) {
            return false;
        }
        String sql = String.format("UPDATE staff\n"
                + "SET name='%s', password='%s'\n"
                + "WHERE id=%d;", stf.getName(), stf.getPW(), stf.getId());
        if (db.updateQuery(sql) == 1) {
            return true;
        }
        return false;
    }

    /**
     * Remove staff from the database<br>
     * NOTE: The removed staff will not appear in other table as well
     *
     * @param staff int
     * @return true if staff was removed successfully
     */
    @Override
    public boolean remove(Staff staff) {
        db.execQuery("UPDATE transactions\n"
                + "SET staffHandled=NULL\n"
                + "WHERE staffHandled=" + staff.getId());
        String sql = String.format("DELETE FROM staff WHERE id=%d", staff.getId());
        Staff[] tmpList = new Staff[ARRAY_SIZE];
        if (db.updateQuery(sql) == 1) {
            int b = 0;
            for (int a = 0; a < staffCount; a++) {
                if (!staffList[a].equals(staff)) {
                    tmpList[b++] = staffList[a];
                }
            }
            staffList = tmpList.clone();
            staffCount--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set logOnStaff to null
     *
     * @see StaffManager#logOnStaff
     */
    public void logOut() {
        this.logOnStaff = null;
    }

    /**
     * @return amount of staff loaded from database
     */
    public int getStaffCount() {
        return staffCount;
    }
}
