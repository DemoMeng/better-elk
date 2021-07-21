package com.mqz.elk.common.model.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 青网科技集团 版权所有 © Copyright 2012<br>
 *
 * @Author： 蒙大拿
 * @Date：
 * @Description
 * @About： https://github.com/DemoMeng
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor()
@NoArgsConstructor()
@Document(indexName = "poem",type = "poem_doc")
public class Poem implements Serializable {

    private String id;

    /**
     * 中文分词设置，前提是您的es已经安装了中文分词ik插件
     * 中文分词有两种形式：
     * ik_max_word：会将文本做最细粒度的拆分
     * ik_smart：会将文本做最粗粒度的拆分
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word",searchAnalyzer ="ik_max_word")
    private String userRemark;

    @Field(type = FieldType.Text, analyzer = "ik_smart",searchAnalyzer ="ik_smart")
    private String content;

    public static List<Poem> poemList = new ArrayList<>();
    static {
        poemList.add(new Poem("1nnnn","杜甫《春望》","国破山河在，城春草木深。感时花溅泪，恨别鸟惊心。烽火连三月，家书抵万金。白头搔更短，浑欲不胜簪。"));
        poemList.add(new Poem("1cccc","李清照《夏日绝句》","生当作人杰，死亦为鬼雄。至今思项羽，不肯过江东。"));
        poemList.add(new Poem("1qqqq","范成大《州桥》","州桥南北是天街，父老年年等驾回。忍泪失声问使者：‘几时真有六军来’"));
        poemList.add(new Poem("1xxxx","林升《题临安邸》","山外青山楼外楼，西湖歌舞几时休。暖风熏得游人醉，直把杭州作汴州。"));
        poemList.add(new Poem("1aaaa","陆游《示儿》","死去原知万事空，但悲不见九州同。王师北定中原日，家祭无忘告乃翁。"));
        poemList.add(new Poem("1ssss","陆游《秋夜将晓出篱门迎凉有感》","三万里河东人海，五千仍岳上摩天。遗民泪尽胡尘里，南望王师又一年。"));
        poemList.add(new Poem("1dddd","文天祥《过零丁洋》","辛苦遭逢起一经，干戈寥落四周星。山河破碎风飘絮，身世浮沉雨打萍。惶恐滩头说惶恐，零丁洋里叹零丁。人生自古谁无死，留取丹心照汗青。"));
        poemList.add(new Poem("1ffff","于谦《石灰吟》","千锤万凿出深山，烈火焚烧若等闲。粉身碎骨浑不怕，要留清白在人间。"));
        poemList.add(new Poem("1wwww","龚自珍《己亥杂诗》(其五)","浩荡离愁白日斜，吟鞭东指即天涯。落红不是无情物，化作春泥更护花。"));
        poemList.add(new Poem("1eeee","元·倪 瓒","秋风兰蕙化为茅！南国凄凉气已消。只有所南心不改，泪泉和墨写《离骚》。"));
        poemList.add(new Poem("1rrrr","《望阙台》 明·戚继光 ","十年驱驰海色寒，孤臣于此望宸銮。繁霜尽是心头血．洒向千峰秋叶丹。"));
        poemList.add(new Poem("1tttt","《马上作》 明·戚继光","南北驱驰报主情，江花边月笑平生。一年三百六十日，多是横戈马上行。"));
        poemList.add(new Poem("1yyyy","《榆河晓发》 明·谢 榛","朝晖开众山，遥见居庸关。云出三边外，风生万马间。征尘何日静，古戍几人闲。忽忆弃繻者，空惭旅鬓斑。"));
        poemList.add(new Poem("1uuuu","《渡易水》 明·陈子龙","并刀昨夜匣中鸣，燕赵悲歌最不平。易水潺湲云草碧，可怜无处送荆卿。"));
        poemList.add(new Poem("1gggg","《海上(选一)》 清·顾炎武","日入空山海气侵，秋光千里自登临。十年天地干戈老，四海苍生痛哭深。水涌神山来白鸟，云浮仙阙见黄金。此中何处无人世，只恐难酬壮士心。"));
        poemList.add(new Poem("1bbbb","《出师讨满夷自瓜州至金陵》清·郑成功","缟素临江誓灭胡，雄师一万气吞吴。试看天堑投鞭渡，不信中原不姓朱！"));
        poemList.add(new Poem("1jjjj","《秣陵》 清·屈大均","牛首开天阙，龙岗抱帝宫。六朝春草里，万井落花中。访旧乌衣少，听歌玉树空。如何亡国恨，尽在大江东。"));
    }


}
