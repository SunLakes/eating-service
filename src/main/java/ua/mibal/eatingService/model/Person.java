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

package ua.mibal.eatingService.model;

import java.util.Set;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Person {

    public static final Person emptyPerson = new Person(-1, -1, "null", Set.of());

    private int id;

    private int braceletId;

    private String name;

    private Set<Integer> days;

    private Person() {
    }

    public Person(int id, int braceletId, String name, Set<Integer> days) {
        this.id = id;
        this.braceletId = braceletId;
        this.name = name;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public int getBraceletId() {
        return braceletId;
    }

    public String getName() {
        return name;
    }

    public boolean isRegisteredForDay(int dayId) {
        return days.contains(dayId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", braceletId=").append(braceletId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", days=").append(days);
        sb.append('}');
        return sb.toString();
    }
}
