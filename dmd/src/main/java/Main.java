import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/dmd20";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "megakek228";

    public static void main(String[] args){
        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e){
            System.err.println("Иди нахой");
        }

        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); Statement statement = connection.createStatement()){
            q3("2018-11-25");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String q1(String col, String num, String usName) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM (SELECT * FROM history_man_car INNER JOIN car ON history_man_car.car_id=car.id) t WHERE (t.car_id LIKE '" + num + "%' AND t.color='" + col + "' AND t.username='" + usName + "')");
        String answer = "";
        while(res.next()){
            answer += res.getString(2) + "; start time: " + res.getString(3) + "; Start location: " + res.getString(5) + "\n";
        }
        statement.close();
        return answer;
    }

    static String q2(String date) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT time, finish FROM history_of_charging WHERE time LIKE '" + date + "%'");
        int[] hours = new int[24];
        while(res.next()){
            String dateTime = res.getString(1);
            dateTime = dateTime.replace(date + " ", "");
            dateTime = dateTime.substring(0, 2);
            int time = Integer.parseInt(dateTime);
            hours[time]++;
            dateTime = res.getString(2);
            dateTime = dateTime.replace(date + " ", "");
            dateTime = dateTime.substring(0, 2);
            int time2 = Integer.parseInt(dateTime);
            while(time != time2) {
                time++;
                hours[time]++;
            }
        }
        String answer = "";
        for(int i = 0; i < 24; i++)
            answer += i + "h-" + (i + 1) + "h: " + hours[i] + "\n";
        statement.close();
        return answer;
    }

    static String q3(String first_day) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res0=statement.executeQuery("SELECT id from car GROUP BY id");
        int counter = 0;
        while(res0.next())
            counter++;
        ResultSet res = statement.executeQuery("SELECT COUNT(car_id) FROM (SELECT car_id FROM history_man_car " +
                "WHERE (WEEK(start_time) = WEEK('" + first_day + "') " +
                "AND HOUR(start_time)>=7 AND HOUR(start_time)<=10) " +
                "OR (WEEK(start_time) = WEEK('"+first_day+"') AND HOUR(finish_time)>=7 AND HOUR(finish_time)<=10)) " +
                "t GROUP BY t.car_id");
        int counterM = 0;
        while(res.next())
            counterM++;
        res = statement.executeQuery("SELECT COUNT(car_id) FROM (SELECT car_id FROM history_man_car " +
                "WHERE (WEEK(start_time) = WEEK('" + first_day + "') " +
                "AND HOUR(start_time)>=12 AND HOUR(start_time)<=14) " +
                "OR (WEEK(start_time) = WEEK('"+first_day+"') AND HOUR(finish_time)>=12 AND HOUR(finish_time)<=14)) " +
                "t GROUP BY t.car_id");
        int counterA = 0;
        while(res.next())
            counterA++;
        res = statement.executeQuery("SELECT COUNT(car_id) FROM (SELECT car_id FROM history_man_car " +
                "WHERE (WEEK(start_time) = WEEK('" + first_day + "') " +
                "AND HOUR(start_time)>=17 AND HOUR(start_time)<=19) " +
                "OR (WEEK(start_time) = WEEK('"+first_day+"') AND HOUR(finish_time)>=17 AND HOUR(finish_time)<=19)) " +
                "t GROUP BY t.car_id");
        int counterE = 0;
        while(res.next())
            counterE++;
        String answer = "Morning:\n" + (int)(((double)counterM/counter) * 100) + "\n" + "Afternoon:\n" + (int)(((double)counterA/counter) * 100) + "\n" + "Evening:\n" + (int)(((double)counterE/counter) * 100) + "\n";
        statement.close();
        return answer;
    }

    static String q4(String usName, String date) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT COUNT(*), car_id, payment " +
                "FROM (SELECT payment, car_id FROM history_man_car WHERE (username='" + usName + "' AND MONTH(start_time)=MONTH('" + date + "') ) ) t " +
                "GROUP BY t.payment, t.car_id HAVING COUNT(*)>1");
        String answer = "";
        while(res.next())
            answer += "Car ID: " + res.getString(2) + "; payment: " + res.getInt(3) + "\n";
        if(answer.length() == 0)
            answer = "No problems";
        statement.close();
        return answer;
    }

    static String q5(String day) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT location_of_start, location_of_car FROM history_man_car WHERE start_time LIKE '" + day + "%'");
        double distance = 0;
        int counter = 0;
        final int R = 6371;
        String answer = "";
        while(res.next()){
            counter++;
            String startCor = res.getString(1);
            double startX = Double.parseDouble(startCor.substring(0, 11));
            double startY = Double.parseDouble(startCor.substring(11, 21));
            String carCor = res.getString(2);
            double carX = Double.parseDouble(carCor.substring(0, 11));
            double carY = Double.parseDouble(carCor.substring(11, 21));
            double latDistance = Math.toRadians(startX - carX);
            double lonDistance = Math.toRadians(startY - carX);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(startX)) * Math.cos(Math.toRadians(carX))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distanceP = R * c;
            distance += Math.pow(distanceP, 2);
        }
        distance = (double)distance/counter;
        res = statement.executeQuery("SELECT AVG(TIMEDIFF(finish_time, start_time)) FROM history_man_car WHERE start_time LIKE '" + day + "%'");
        int hour = 0;
        int min = 0;
        int sec = 0;
        while(res.next()){
            String duration = res.getString(1).replace(".0000", "");
            int l = duration.length();
            sec = Integer.parseInt(duration.substring((l - 2), l));
            min = Integer.parseInt(duration.substring((l - 4), (l - 2)));
            hour = Integer.parseInt(duration.substring(0, (l - 4)));
        }
        answer += distance + "km; " + hour + ":" + min + ":" + sec;
        statement.close();
        return answer;
    }

    static String q6() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT location_of_start, HOUR(start_time), HOUR(finish_time) FROM history_man_car");
        double[][] corsM = new double[0][2];
        int[] counterM = new int[0];
        double[][] corsA = new double[0][2];
        int[] counterA = new int[0];
        double[][] corsE = new double[0][2];
        int[] counterE = new int[0];
        while(res.next()){
            if(res.getString(1).length() == 21){
                if(res.getInt(2) >= 7 && res.getInt(2) <= 10 || res.getInt(3) >= 7 && res.getInt(3) <= 10){
                    int[] counter20 = new int[counterM.length + 1];
                    counterM = counter20.clone();
                    double[][] cors20 = new double[corsM.length + 1][2];
                    for (int i = 0; i < corsM.length; i++) {
                        cors20[i] = corsM[i].clone();
                    }
                    corsM = cors20.clone();
                    String cor = res.getString(1);
                    corsM[corsM.length - 1][0] = Double.parseDouble(cor.substring(0, 11));
                    corsM[corsM.length - 1][1] = Double.parseDouble(cor.substring(11, 21));
                }
                if(res.getInt(2) >= 12 && res.getInt(2) <= 14 || res.getInt(3) >= 12 && res.getInt(3) <= 14){
                    int[] counter20 = new int[counterA.length + 1];
                    counterA = counter20.clone();
                    double[][] cors20 = new double[corsA.length + 1][2];
                    for (int i = 0; i < corsA.length; i++) {
                        cors20[i] = corsA[i].clone();
                    }
                    corsA = cors20.clone();
                    String cor = res.getString(1);
                    corsA[corsA.length - 1][0] = Double.parseDouble(cor.substring(0, 11));
                    corsA[corsA.length - 1][1] = Double.parseDouble(cor.substring(11, 21));
                }
                if(res.getInt(2) >= 17 && res.getInt(2) <= 19 || res.getInt(3) >= 17 && res.getInt(3) <= 19){
                    int[] counter20 = new int[counterE.length + 1];
                    counterE = counter20.clone();
                    double[][] cors20 = new double[corsE.length + 1][2];
                    for (int i = 0; i < corsE.length; i++) {
                        cors20[i] = corsE[i].clone();
                    }
                    corsE = cors20.clone();
                    String cor = res.getString(1);
                    corsE[corsE.length - 1][0] = Double.parseDouble(cor.substring(0, 11));
                    corsE[corsE.length - 1][1] = Double.parseDouble(cor.substring(11, 21));
                }
            }
        }
        for(int i = 0; i < corsM.length - 1; i++){
            if(corsM[i][0] != -1) {
                for (int j = i + 1; j < corsM.length; j++) {
                    if (corsM[j][0] != -1 && (corsM[i][0] - 1) <= corsM[j][0] && (corsM[i][0] + 1) >= corsM[j][0] && (corsM[i][1] - 1) <= corsM[j][1] && (corsM[i][1] + 1) >= corsM[j][1]) {
                        counterM[i]++;
                        corsM[j][0] = -1;
                    }
                }
            }
        }
        int max1M = 0;
        while(corsM[max1M][0] == -1)
            max1M++;
        int max2M = max1M;
        int max3M = max1M;
        for(int i = 3; i < counterM.length; i++){
            if(corsM[i][0] != -1) {
                if (corsM[max1M][0] != corsM[i][0] && counterM[i] > counterM[max1M]) {
                    max3M = max2M;
                    max2M = max1M;
                    max1M = i;
                } else if (corsM[max3M][0] != corsM[i][0] && counterM[i] > counterM[max2M]) {
                    max3M = max2M;
                    max2M = i;
                } else if (corsM[max3M][0] != corsM[i][0] && counterM[i] > counterM[max3M]) {
                    max3M = i;
                }
            }
        }
        for(int i = 0; i < corsA.length - 1; i++){
            if(corsA[i][0] != -1) {
                for (int j = i + 1; j < corsA.length; j++) {
                    if (corsA[j][0] != -1 && (corsA[i][0] - 1) <= corsA[j][0] && (corsA[i][0] + 1) >= corsA[j][0] && (corsA[i][1] - 1) <= corsA[j][1] && (corsA[i][1] + 1) >= corsA[j][1]) {
                        counterA[i]++;
                        corsA[j][0] = -1;
                    }
                }
            }
        }
        int max1A = 0;
        while(corsA[max1A][0] == -1)
            max1A++;
        int max2A = max1A;
        int max3A = max1A;
        for(int i = 3; i < counterA.length; i++){
            if(corsA[i][0] != -1) {
                if (corsA[max1A][0] != corsA[i][0] && counterA[i] > counterA[max1A]) {
                    max3A = max2A;
                    max2A = max1A;
                    max1A = i;
                } else if (corsA[max2A][0] != corsA[i][0] && counterA[i] > counterA[max2A]) {
                    max3A = max2A;
                    max2A = i;
                } else if (corsA[max3A][0] != corsA[i][0] && counterA[i] > counterA[max3A]) {
                    max3A = i;
                }
            }
        }
        for(int i = 0; i < corsE.length - 1; i++){
            if(corsE[i][0] != -1) {
                for (int j = i + 1; j < corsE.length; j++) {
                    if (corsE[j][0] != -1 && (corsE[i][0] - 1) <= corsE[j][0] && (corsE[i][0] + 1) >= corsE[j][0] && (corsE[i][1] - 1) <= corsE[j][1] && (corsE[i][1] + 1) >= corsE[j][1]) {
                        counterE[i]++;
                        corsE[j][0] = -1;
                    }
                }
            }
        }
        int max1E = 0;
        while(corsE[max1E][0] == -1)
            max1E++;
        int max2E = max1E;
        while(corsE[max2E][0] == -1)
            max2E++;
        int max3E = max2E;
        while(corsE[max3E][0] == -1)
            max3E++;
        for(int i = 3; i < counterE.length; i++){
            if(corsE[i][0] != -1){
                if (corsE[max1E][0] != corsE[i][0] && counterE[i] > counterE[max1E]) {
                    max3E = max2E;
                    max2E = max1E;
                    max1E = i;
                } else if (corsE[max2E][0] != corsE[i][0] && counterE[i] > counterE[max2E]) {
                    max3E = max2E;
                    max2E = i;
                } else if (corsE[max3E][0] != corsE[i][0] && counterE[i] > counterE[max3E]) {
                    max3E = i;
                }
            }
        }
        statement.close();
        String answer = "Morning:\n" + corsM[max1M][0] + " " + corsM[max1M][1] + "\n" + corsM[max2M][0] + " " + corsM[max2M][1] + "\n" + corsM[max3M][0] + " " + corsM[max3M][1] + "\nAfternoon:\n" + corsA[max1A][0] + " " + corsA[max1A][1] + "\n" + corsA[max2A][0] + " " + corsA[max2A][1] + "\n" + corsA[max3A][0] + " " + corsA[max3A][1] + "\nEvening:\n" + corsE[max1E][0] + " " + corsE[max1E][1] + "\n" + corsE[max2E][0] + " " + corsE[max2E][1] + "\n" + corsE[max3E][0] + " " + corsE[max3E][1];
        return answer;
    }

    static String q7(String date) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT car_id, COUNT(car_id) " +
                "FROM history_man_car " +
                "WHERE (MONTH (start_time)=MONTH ('"+date+"') or MONTH (start_time)=MONTH('" + date + "') - 1 or MONTH (start_time)=MONTH('" + date + "') - 2) " +
                "GROUP BY car_id " +
                "ORDER BY COUNT(car_id) ASC ");
        String[] cars = new String[0];
        while(res.next()){
            String[] cars20 = new String[cars.length + 1];
            for(int i = 0; i < cars.length; i++){
                cars20[i] = cars[i];
            }
            cars = cars20.clone();
            cars[cars.length - 1] = res.getString(1);
        }
        String answer = "";
        for(int i = 0; i <= cars.length * 0.1; i++){
            answer += cars[i] + "\n";
        }
        return answer;
    }

    static String q8(String date) throws SQLException {
        String out="";
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT t.username, t.car_id FROM (SELECT username, car_id, start_time FROM history_man_car GROUP BY history_man_car.car_id) t INNER JOIN history_of_charging ON t.car_id = history_of_charging.car_id WHERE MONTH(t.start_time) = MONTH('" + date + "') AND MONTH(history_of_charging.time) = MONTH('" + date + "')");
        String[][] users = new String[0][2];
        while(res.next()){
            String username = res.getString(1);
            boolean isHere = false;
            for(int i = 0; i < users.length; i++){
                if(users[i][0].equals(username)){
                    users[i][1] = Integer.toString(Integer.parseInt(users[i][1]) + 1);
                    isHere = true;
                    break;
                }
            }
            if(!isHere) {
                String[][] users20 = new String[users.length + 1][2];
                for(int i = 0; i < users.length; i++){
                    users20[i] = users[i].clone();
                }
                users = users20.clone();
                users[users.length - 1][0] = username;
                users[users.length - 1][1] = "1";
            }
        }
        for(int i = 0; i < users.length; i++){
            out+=users[i][0]+" "+Integer.parseInt(users[i][1])+"\n";
        }
        statement.close();
        return out;
    }

    static void q9() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet res = statement.executeQuery("SELECT history_of_repairing.part_type, WEEK(history_of_repairing.time), history_of_repairing.wid FROM history_of_repairing");
        String[][] wwp = new String[0][3];
        while(res.next()) {
            String[][] wwp20 = new String[wwp.length + 1][3];
            for (int i = 0; i < wwp.length; i++) {
                wwp20[i] = wwp[i].clone();
            }
            wwp = wwp20.clone();
            wwp[wwp.length - 1][0] = res.getString(1);
            wwp[wwp.length - 1][1] = Integer.toString(res.getInt(2));
            wwp[wwp.length - 1][2] = res.getString(3);
        }
        String[][] parts = new String[0][2];
        for(int i = 0; i < wwp.length; i++){
            boolean isHere = false;
            for(int j = 0; j < parts.length; j++){
                if(wwp[i][0].equals(parts[j][0])) {
                    isHere = true;
                    break;
                }
            }
            if(!isHere){
                String[][] parts20 = new String[parts.length + 1][2];
                for (int j = 0; j < parts.length; j++) {
                    parts20[j] = parts[j].clone();
                }
                parts = parts20.clone();
                parts[parts.length - 1][0] = wwp[i][0];
                parts[parts.length - 1][1] = "0";
            }
        }
        String[][] result = new String[0][4];
        for(int i = 0; i < wwp.length - 1; i++){
            for(int k = 0; k < parts.length; k++)
                parts[k][1] = "0";
            for(int k = 0; k < parts.length; k++){
                if(parts[k][0].equals(wwp[i][0])){
                    parts[k][1] = Integer.toString(Integer.parseInt(parts[k][1] + 1));
                    break;
                }
            }
            for(int j = i + 1; j < wwp.length; j++){
                if(wwp[i][0].equals(wwp[j][0]) && wwp[i][2].equals(wwp[j][2])){
                    for(int k = 0; k < parts.length; k++){
                        if(parts[k][0].equals(wwp[j][0])){
                            parts[k][1] = Integer.toString(Integer.parseInt(parts[k][1]) + 1);
                            break;
                        }
                    }
                }
            }
            int max = 0;
            for(int k = 1; k < parts.length; k++){
                if(Integer.parseInt(parts[k][1]) > Integer.parseInt(parts[max][1]))
                    max = k;
            }
            boolean isHere = false;
            for(int k = 0; k < result.length; k++){
                if(result[k][2].equals(wwp[i][2]) && result[k][1].equals(wwp[i][1])){
                    isHere = true;
                    break;
                }
            }
            if(!isHere){
                String[][] result20 = new String[result.length + 1][4];
                for (int k = 0; k < result.length; k++) {
                    result20[k] = result[k].clone();
                }
                result = result20.clone();
                result[result.length - 1][0] = parts[max][0];
                result[result.length - 1][1] = wwp[i][1];
                result[result.length - 1][2] = wwp[i][2];
                result[result.length - 1][3] = parts[max][1];
            }
        }
        String[][] resultE = new String[0][3];
        for(int i = 0; i < result.length - 1; i++){
            for(int k = 0; k < parts.length; k++){
                parts[k][1] = "0";
                if(parts[k][1].equals(result[i][0]))
                    parts[k][1] = result[i][3];
            }
            for(int j = i + 1; j < result.length; j++){
                if(result[j][1].equals(result[i][1]) && result[j][2].equals(result[i][2])){
                    for(int k = 0; k < parts.length; k++){
                        if(parts[k][0].equals(result[j][0])) {
                            parts[k][1] = Integer.toString(Integer.parseInt(parts[k][1]) + Integer.parseInt(result[j][3]));
                            break;
                        }
                    }
                }
            }
            int max = 0;
            for(int k = 1; k < parts.length; k++){
                if(Integer.parseInt(parts[k][1]) > Integer.parseInt(parts[max][1]))
                    max = k;
            }
            boolean isHere = false;
            for(int k = 0; k < resultE.length; k++){
                if(resultE[k][1].equals(result[i][1]) && resultE[k][2].equals(result[i][2])){
                    isHere = true;
                    break;
                }
            }
            if(!isHere){
                String[][] resultE20 = new String[resultE.length + 1][4];
                for (int k = 0; k < resultE.length; k++) {
                    resultE20[k] = resultE[k].clone();
                }
                resultE = resultE20.clone();
                resultE[resultE.length - 1][0] = parts[max][0];
                resultE[resultE.length - 1][1] = result[i][2];
                int counter = 0;
                //resultE[resultE.length - 1][0] = parts[max][0];
            }
        }
        for(int i = 0; i < resultE.length; i++){
            System.out.print(resultE[i][0]);
            System.out.print(" ");
            System.out.print(resultE[i][1]);
            System.out.print(" ");
            System.out.println(resultE[i][2]);
        }
        statement.close();
    }

    static void q10(String date) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        //ResultSet res = statement.executeQuery("SELECT t3.car_id, MAX(SUM(t.avg_xost, t2.avg_xost)) FROM ((SELECT car_id, AVG(cost) AS avg_cost FROM history_of_repairing GROUP BY car_id) t FULL JOIN (SELECT car_id, AVG(cost) AS avg_cost2 FROM history_of_charging GROUP BY car_id) t2 ON t.car_id = t2.car_id) t3");
//        ResultSet res = statement.executeQuery("SELECT new_t.car_id, (new_t.avg_ch + new_t.avg_rep) " +
//                "FROM (SELECT car_id, AVG(cost) As avg_ch FROM history_of_charging GROUP BY car_id) ch " +
//                "FULL OUTER JOIN (SELECT AVG(cost) AS avg_rep FROM history_of_repairing GROUP BY car_id) rep ON ch.car_id=rep.car_id");
        ResultSet res = statement.executeQuery("SELECT car_id FROM history_of_charging " +
                "FULL JOIN history_of_repairing");
//        ResultSet res = statement.executeQuery("SELECT a.* " +
//                "FROM history_of_charging a " +
//                "LEFT OUTER JOIN history_of_charging b " +
//                "ON a.car_id = b.car_id AND a.cost < b.cost " +
//                "WHERE b.car_id IS NULL");
//        SELECT t2.car_id, SUM(costC) + ISNULL(SUM(costR), 0)
//        FROM t2
//        INNER JOIN t2_EMP ON (SELECT car_id, AVG(cost) As avg_cost FROM history_of_charging GROUP BY car_id).car_id=t2.car_id
//        INNER JOIN EMP ON (SELECT car_id, AVG(cost) As avg_cost FROM history_of_charging GROUP BY car_id).ID_Emp=EMP.ID_Emp
//        LEFT JOIN BONUS ON BONUS.ID_Emp=EMP.ID_Emp
//        GROUP BY PROJECT.Project
        while (res.next()){
            System.out.println(res.getString(1));
        }
        statement.close();
    }
}
