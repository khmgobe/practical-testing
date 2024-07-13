package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class GuavaLearningTest {

    @Test
    void 주어진_개수만큼_List를_파티셔닝한다()  {

        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3), List.of(4,5,6)
                ));
    }

    @Test
    void 주어진_개수만큼_List를_4로_파티셔닝한다()  {

        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                        List.of(1,2,3,4), List.of(5,6)
                ));
    }


    @Test
    void 멀티맵_기능_테스트()  {

        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();

        multimap.put("커피", "아메리카노");
        multimap.put("커피", "필터커피");
        multimap.put("베이커리", "크루키");

        // when
        Collection<String> strings = multimap.get("커피");

        // then
        assertThat(strings).hasSize(2)
                .isEqualTo(List.of("아메리카노", "필터커피"));
    }
}
