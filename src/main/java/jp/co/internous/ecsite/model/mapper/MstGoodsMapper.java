package jp.co.internous.ecsite.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.ecsite.model.domain.MstGoods;

@Mapper
public interface MstGoodsMapper {
	
//	データベースから"mst_goods"テーブルのすべての商品情報を取ってくる
	@Select(value="SELECT * FROM mst_goods")
	List<MstGoods> findAll();
	
	@Insert("INSERT INTO mst_goods (goods_name, price) VALUES (#{goodsName}, #{price})")
//	DB側で自動採番するよう設定 (id="Auto_increment"が条件)
	@Options(useGeneratedKeys=true, keyProperty="id")
//	goodsは、クラス MstGoods のインスタンスを表し、自動採番なので、int型で返す
	int insert(MstGoods goods);
	
//	商品のID（番号）を使って、特定の商品を削除
	@Update("DELETE FROM mst_goods WHERE id = #{ id }")
	int deleteById(int id);
	
}
