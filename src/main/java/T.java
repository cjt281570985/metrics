import com.codahale.metrics.RatioGauge;

/**
 * @Author: chenjt
 * @Description:
 * @Date: Created 2019-10-13 0:36
 */
public class T {

    public static void main(String[] args) {

        RatioGauge.Ratio of = RatioGauge.Ratio.of(6, 9);
        System.out.println(of);


    }

}

