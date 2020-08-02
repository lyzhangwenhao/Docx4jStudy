package com.tom.pojo;

import java.io.Serializable;

/****
 * 文件存储
 * @author louph
 *
 */
public class File_path implements Serializable {
	private int id;//编号
	private int type;//1:任务单；2：生产采购；3：项目采购；4：外协生产流程；5：生产流程；6：发货
	private int foreign_id;//其他表主键id
	private String file_name;//文件名称
	private String path_name;//文件的存储名称
	/******
	 * type=1：任务单；（1:供货清单；2：技术附件；3：项目材料配置单；4：其他）
	 * type=2：生产采购； （1：预计到货时间附件；2实际到货附件；3：其他）
	 * type=3：项目采购； （1:项目采购预算表；2：预计到货时间附件；3实际到货附件；4：其他）
	 * type=4：外协生产流程；   (1:创建上传附件；2：预计到货附件；3：实际到货附件；4：其他)
	 * type=5：生产流程；（1:测试报告;2:其他（关联device表））
	 * type=6：发货 （1：出场检验记录表；2：设备装箱清单；3：现场开箱验货报告；4：财务发货清单；5：回执单）
	 */
	private int file_type;//文件类型
	private long size;//文件大小 如：1024 表示1M
	private String size_String;
	private int format;//文件格式 1：图片；2：文档；3：其他
	private int state;//文件来源 0：当前任务单 1：修改前任务单（type！=1时，流程无需修改，state=0）
	private int uid;//创建者id
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	private long create_time;//创建时间戳
	private String create_date;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getForeign_id() {
		return foreign_id;
	}
	public void setForeign_id(int foreign_id) {
		this.foreign_id = foreign_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getPath_name() {
		return path_name;
	}
	public void setPath_name(String path_name) {
		this.path_name = path_name;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getFormat() {
		return format;
	}
	public void setFormat(int format) {
		this.format = format;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getSize_String() {
		return size_String;
	}
	public void setSize_String(String size_String) {
		this.size_String = size_String;
	}

	@Override
	public String toString() {
		return "File_path{" +
				"id=" + id +
				", type=" + type +
				", foreign_id=" + foreign_id +
				", file_name='" + file_name + '\'' +
				", path_name='" + path_name + '\'' +
				", file_type=" + file_type +
				", size=" + size +
				", size_String='" + size_String + '\'' +
				", format=" + format +
				", state=" + state +
				", uid=" + uid +
				", create_time=" + create_time +
				", create_date='" + create_date + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		File_path file_path = (File_path) o;

		if (id != file_path.id) return false;
		if (type != file_path.type) return false;
		if (foreign_id != file_path.foreign_id) return false;
		if (file_type != file_path.file_type) return false;
		if (size != file_path.size) return false;
		if (format != file_path.format) return false;
		if (uid != file_path.uid) return false;
		if (!file_name.equals(file_path.file_name)) return false;
		if (!path_name.equals(file_path.path_name)) return false;
		if (!size_String.equals(file_path.size_String)) return false;
		else return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + type;
		result = 31 * result + foreign_id;
		result = 31 * result + file_name.hashCode();
		result = 31 * result + path_name.hashCode();
		result = 31 * result + file_type;
		result = 31 * result + (int) (size ^ (size >>> 32));
		result = 31 * result + size_String.hashCode();
		result = 31 * result + format;
		result = 31 * result + state;
		result = 31 * result + uid;
		return result;
	}
}
