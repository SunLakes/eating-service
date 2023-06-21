/*
 * Copyright (c) 2023. http://t.me/mibal_ua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.mibal.peopleService.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mibal.peopleService.model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@Component
public class PersonDao {

    private final Connection connection;

    public PersonDao(@Value("${jdbcDriverPackage}") final String jdbcDriverPackage,
                     @Value("${spring.datasource.url}") final String dbUrl,
                     @Value("${spring.datasource.username}") final String user,
                     @Value("${spring.datasource.password}") final String password) {
        try {
            Class.forName(jdbcDriverPackage);
            this.connection = DriverManager.getConnection(
                    dbUrl,
                    user,
                    password
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Optional<Person> getByBraceletId(final int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, bracelet_id, name, days " +
                    "FROM people " +
                    "WHERE bracelet_id = ? " +
                    "LIMIT 1"
            );
            preparedStatement.setObject(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(new Person(
                    resultSet.getInt("id"),
                    resultSet.getInt("bracelet_id"),
                    resultSet.getString("name"),
                    Arrays.stream(resultSet.getString("days")
                                    .split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet())
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
