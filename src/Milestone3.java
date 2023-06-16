import java.sql.*;
import java.util.Scanner;

public class Milestone3 {
  public static void main(String[] args) {
//    parametri connessione
    String url= "jdbc:mysql://localhost:3306/java_nations";
    String user = "root";
    String  password = "root";
    
    String query = """
      SELECT countries.name as country_name, countries.country_id as country_id,regions.name as region_name, continents.name as continent_name
      FROM `countries`
      JOIN regions ON regions.region_id = countries.region_id
      JOIN continents ON continents.continent_id = regions.continent_id
      WHERE countries.name LIKE ?
      ORDER BY countries.name ASC;
      """;
    String query2 = """
      SELECT countries.name AS country_name, countries.country_id AS country_id, GROUP_CONCAT(languages.language) AS languages
      FROM `countries`
      JOIN country_languages ON country_languages.country_id = countries.country_id
      JOIN languages ON country_languages.language_id = languages.language_id
      WHERE countries.country_id = ?
      GROUP BY countries.name, countries.country_id;
      """;
    try (Connection conn = DriverManager.getConnection(url, user, password)){
      try (PreparedStatement ps = conn.prepareStatement(query)) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cerca un paese: ");
        String param = scanner.nextLine();
        
        ps.setString(1, "%" + param + "%");
        
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            String country = rs.getString("country_name");
            String country_id = rs.getString("country_id");
            String region = rs.getString("region_name");
            String continent = rs.getString("continent_name");
            System.out.print("Country ID: " + country_id + ", ");
            System.out.print("Country Name: "+ country + ", ");
            System.out.print("Region Name: "+ region + ", ");
            System.out.print("Continent Name: "+ continent + "\n");
          }
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
        Scanner scanner2 = new Scanner(System.in);
        System.out.print("ID paese: ");
        String param2 = scanner2.nextLine();
        
        ps2.setString(1, param2);
        scanner2.close();
        try (ResultSet rs2 = ps2.executeQuery()) {
          while (rs2.next()) {
            String country = rs2.getString("country_name");
            String country_id = rs2.getString("country_id");
            String languages = rs2.getString("languages");
            
            System.out.print("Country ID: " + country_id + ", ");
            System.out.print("Country Name: "+ country + ", ");
            System.out.print("Languages: "+ languages + "\n");
          }
        }
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    
  }
}
