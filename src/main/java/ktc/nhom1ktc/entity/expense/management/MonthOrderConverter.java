package ktc.nhom1ktc.entity.expense.management;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Month;

@Converter(autoApply = true)
public class MonthOrderConverter implements AttributeConverter<Month, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Month month) {
        return month.getValue();
    }

    @Override
    public Month convertToEntityAttribute(Integer integer) {
        return Month.of(integer);
    }
}
