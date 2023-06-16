import java.sql.*;

public class Milestone2 {
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
      ORDER BY countries.name ASC;
      """;
    try (Connection conn = DriverManager.getConnection(url, user, password)){
      try (PreparedStatement ps = conn.prepareStatement(query)){
        try(ResultSet rs = ps.executeQuery()) {
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
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
