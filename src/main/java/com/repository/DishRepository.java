package com.repository;

@Repository
public class DishRepository {

    private final Connection connection;

    public DishRepository(Connection connection) {
        this.connection = connection;
    }

    // vérifier si existe
    public boolean existsByName(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM dish WHERE name = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    // save
    public Dish save(Dish dish) throws SQLException {
        String sql = "INSERT INTO dish(name, category, price) VALUES (?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, dish.getName());
        stmt.setString(2, dish.getCategory());
        stmt.setDouble(3, dish.getPrice());

        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if (keys.next()) {
            dish.setId(keys.getLong(1));
        }

        return dish;
    }

    // findAll
    public List<Dish> findAll() throws SQLException {
        List<Dish> list = new ArrayList<>();

        String sql = "SELECT * FROM dish";
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Dish d = new Dish();
            d.setId(rs.getLong("id"));
            d.setName(rs.getString("name"));
            d.setCategory(rs.getString("category"));
            d.setPrice(rs.getDouble("price"));
            list.add(d);
        }

        return list;
    }
}