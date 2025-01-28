package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.domain.MstGoods;
import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;
import jp.co.internous.ecsite.model.mapper.MstGoodsMapper;
import jp.co.internous.ecsite.model.mapper.MstUserMapper;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstGoodsMapper goodsMapper;
	
	@RequestMapping("/")
	public String index() {
		return "admintop";
	}
	
//	@PostMapping = POSTリクエストをこのメソッドが処理するという目印
	@PostMapping("/welcome")
//	LoginForm form：送られてきたデータ     Model model：作成したデータをビューに返す
	public String welcome(LoginForm form, Model model) {
		
//		ログイン情報によってユーザーを検索する処理(MstUserMapperのSQL文が実行される)
//		SQL文の実行結果がuserインスタンスに格納される
		MstUser user = userMapper.findByUserNameAndPassword(form);
		
//		検索結果がnull(該当なし)かどうか
		if (user == null) {
//			model.addAttribute("key", value); (ビューにデータを渡すときに使う)
			model.addAttribute("errMessage", "ユーザー名またはパスワードが違います。");
			return "forward:/ecsite/admin/";
		}
		
//		ログインユーザー (0) が管理者 (1) かどうか
		if (user.getIsAdmin() == 0) {
			model.addAttribute("errMessage", "管理者ではありません。");
			return "forward:/ecsite/admin/";
		}
		
		
//		ここに到達したら「ログイン成功 ＆ 管理者でのログイン」の条件が保証
		List<MstGoods> goods = goodsMapper.findAll();
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("goods", goods);
		
		return "welcome";
	}
	
	@PostMapping("/goodsMst")
	public String goodsMst(LoginForm f, Model m) {
		m.addAttribute("userName", f.getUserName());
		m.addAttribute("password", f.getPassword());
		
		return "goodsmst";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		MstGoods goods = new MstGoods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		
		goodsMapper.insert(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		try {
			goodsMapper.deleteById(f.getId());
		} catch (IllegalArgumentException e) {
//			-1 = 「処理に失敗した」
			return "-1";
		}
//		1 = 「処理が成功した」
		return "1";
	}
}









