package com.example.demo.form;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.SiteUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SiteUserForm {

	private static final int AVATAR_MAX_WIDTH = 200;

	private Long id;

	@Size(min = 2, max = 20, message="名前は、3文字から、20文字の間で入力してください。")
	private String username;

	@Size(min = 4, max = 255, message="パスワードは、5文字から255文字の間で入力してください。")
	private String password;

	@NotBlank(message = "ロールを選択してください。")
	private String role;

	private MultipartFile avatar;

	private String avatarSrc;

	public SiteUserForm() {
		id = null;
		username = "";
		password = null;
		role = "";
		avatar = null;
		avatarSrc = "";
	}

	public SiteUserForm(SiteUser user) {
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		role = user.getRole();
		avatar = null;
		avatarSrc = user.getAvatar();
	}

	public SiteUser toEntity() {
		SiteUser user = new SiteUser();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		//avatar = null;
		//avatarSrc = siteUser.getAvatar();
		return user;
		}

	public Optional<String> loadAvaterSrc() {
		if (avatar.isEmpty()) return Optional.empty();
		try {
			// get the uploaded file
			BufferedImage img = ImageIO.read(avatar.getInputStream());
			// shrink it if it's too large
			if (img.getWidth() > AVATAR_MAX_WIDTH) {
				img = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC,
						AVATAR_MAX_WIDTH,
						Scalr.OP_ANTIALIAS);
			}
			// make it byte[]
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, avatar.getContentType().split("/")[1], out);
			out.flush();
			byte[] raw = out.toByteArray();
			// convert for src attribute
			String src = "data:image/png;base64," + Base64.getEncoder().encodeToString(raw);
			return Optional.of(src);
		} catch (IOException e) {
			return Optional.empty();
		}
	}
}
