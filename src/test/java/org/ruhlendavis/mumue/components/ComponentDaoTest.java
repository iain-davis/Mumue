package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.acceptance.DatabaseHelper;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class ComponentDaoTest {
    private final QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
    private final ComponentDao dao = new ComponentDao();

    @Test
    public void whenNotFoundReturnNotFound() {
        Component component = dao.getComponent(0);

        assertThat(component.getId(), equalTo(GlobalConstants.REFERENCE_NOT_FOUND));
    }

    @Test
    public void returnComponentWithGivenId() {
        long id = RandomUtils.nextLong(100, 200);

        insertComponent(id);

        Component component = dao.getComponent(id);

        assertThat(component.getId(), equalTo(id));
    }

    private void insertComponent(long id) {
        String sql = "insert into components (id)"
                + " values (" + id + ");";
        try {
            queryRunner.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
