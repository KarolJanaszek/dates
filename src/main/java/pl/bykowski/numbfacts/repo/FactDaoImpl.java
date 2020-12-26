package pl.bykowski.numbfacts.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.bykowski.numbfacts.model.Fact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FactDaoImpl implements FactDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public FactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Fact> getAllFact() {

        String sql = "SELECT * FROM numb_facts";

        List<Fact> factList = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.stream().forEach(element -> factList.add(new Fact(
                Long.parseLong(String.valueOf(element.get("id"))),
                String.valueOf(element.get("text")),
                (Integer) element.get("number")
        )));
        return factList;
    }

    @Override
    public Fact getFactById(Long id) {
        String sql = "SELECT * FROM numb_facts WHERE ID = ?";
        Fact fact = jdbcTemplate.queryForObject(sql,
                (rs, i) -> new Fact(rs.getLong("id"), rs.getString("text"),
                        rs.getInt("number"))
                , id);
        return fact;
    }

    @Override
    public void saveFact(Fact fact) {
        try {
            String sql = "INSERT INTO numb_facts VALUES(null,?,?)";
            jdbcTemplate.update(sql, fact.getText(), fact.getNumber());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editFactText(Fact fact) {
        String sql = "UPDATE numb_facts nb SET nb.TEXT = ? WHERE nb.ID = ?";
        jdbcTemplate.update(sql, fact.getText(), fact.getId());
    }

    @Override
    public void deleteFactById(Long id) {
        String sql = "DELETE FROM numb_facts WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
