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

package ua.mibal.peopleService.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class Entry {

    @NotNull(message = "Provide a bracelet id")
    @Min(value = 1, message = "Bracelet id must be greater than 0")
    private Integer braceletId;

    @NotNull(message = "Provide a day id")
    @Min(value = 1, message = "Day id must be in range [1, 7]")
    @Max(value = 7, message = "Day id must be in range [1, 7]")
    private Integer dayId;

    @NotNull(message = "Provide an eating id")
    @Min(value = 1, message = "Eating id must be in range [1, 3]")
    @Max(value = 3, message = "Eating id must be in range [1, 3]")
    private Integer eatingId;

    public Entry(@NotNull(message = "Provide a bracelet id") Integer braceletId,
                 @NotNull(message = "Provide a day id") Integer dayId,
                 @NotNull(message = "Provide an eating id") Integer eatingId) {
        this.braceletId = braceletId;
        this.dayId = dayId;
        this.eatingId = eatingId;
    }

    private Entry() {
    }

    public Integer getBraceletId() {
        return braceletId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public Integer getEatingId() {
        return eatingId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entry{");
        sb.append("braceletId=").append(braceletId);
        sb.append(", dayId=").append(dayId);
        sb.append(", eatingId=").append(eatingId);
        sb.append('}');
        return sb.toString();
    }
}
