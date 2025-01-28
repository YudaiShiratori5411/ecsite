package jp.co.internous.ecsite.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.ecsite.model.domain.MstUser;
import jp.co.internous.ecsite.model.form.LoginForm;

@Mapper
public interface MstUserMapper {
	
	@Select(value="SELECT * FROM mst_user WHERE user_name = #{userName} AND password = #{password}")
//	LoginForm オブジェクトを引数として渡し、DBの mst_user テーブルから一致するユーザー情報を見つけ、MstUser型で返す
	MstUser findByUserNameAndPassword(LoginForm form);
	
}
