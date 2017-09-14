package com.ddebbie.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
                
/**
 * Constants for various file types understood by the FileService
 * 
 */
/**
 * @author Ram
 * 13-Sep-2017
 */
public class FileType implements Serializable {
	private static final long serialVersionUID = 746179225093466487L;
	private static Map<Integer, FileType> map = new HashMap<Integer, FileType>();
	private static Map<String, String> MIME_TYPE_MAP = new HashMap<String, String>();
	private static Map<String, Integer> EXT_CAT_MAP = new HashMap<String, Integer>();

    public static final String H264_FORMAT = "h264";

	/* categories */
	public static final int CATEGORY_IMAGE = 1 << 16;
	public static final int CATEGORY_TEXT = 1 << 17;
	public static final int CATEGORY_AUDIO = 1 << 18;
	public static final int CATEGORY_VIDEO = 1 << 19;
	public static final int CATEGORY_UNKNOWN = 1 << 20;
	public static final int CATEGORY_THUMBNAIL = 1 << 21;
	public static final int CATEGORY_EXECUTABLE = 1 << 22;
	public static final int CATEGORY_FLASH = 1 << 23;
	public static final int CATEGORY_ZIP = 1 << 24;
	public static final int CATEGORY_CSV = 1 << 25;
	public static final int CATEGORY_RAR = 1 << 26;

	/**
	 * mime type mapping compiled from
	 * http://www.duke.edu/websrv/file-extensions.html
	 */
	static {
		MIME_TYPE_MAP.put("ai", "application/postscript");
		MIME_TYPE_MAP.put("aif", "audio/x-aiff");
		MIME_TYPE_MAP.put("aifc", "audio/x-aiff");
		MIME_TYPE_MAP.put("aiff", "audio/x-aiff");
		MIME_TYPE_MAP.put("asc", "text/plain");
		MIME_TYPE_MAP.put("au", "audio/basic");
		MIME_TYPE_MAP.put("avi", "video/x-msvideo");
		MIME_TYPE_MAP.put("bcpio", "application/x-bcpio");
		MIME_TYPE_MAP.put("bin", "application/octet-stream");
		MIME_TYPE_MAP.put("c", "text/plain");
		MIME_TYPE_MAP.put("cc", "text/plain");
		MIME_TYPE_MAP.put("ccad", "application/clariscad");
		MIME_TYPE_MAP.put("cdf", "application/x-netcdf");
		MIME_TYPE_MAP.put("class", "application/octet-stream");
		MIME_TYPE_MAP.put("cpio", "application/x-cpio");
		MIME_TYPE_MAP.put("cpt", "application/mac-compactpro");
		MIME_TYPE_MAP.put("csh", "application/x-csh");
		MIME_TYPE_MAP.put("css", "text/css");
		MIME_TYPE_MAP.put("csv", "text/plain");
		MIME_TYPE_MAP.put("dcr", "application/x-director");
		MIME_TYPE_MAP.put("dir", "application/x-director");
		MIME_TYPE_MAP.put("dms", "application/octet-stream");
		MIME_TYPE_MAP.put("doc", "application/msword");
		MIME_TYPE_MAP.put("drw", "application/drafting");
		MIME_TYPE_MAP.put("dvi", "application/x-dvi");
		MIME_TYPE_MAP.put("dwg", "application/acad");
		MIME_TYPE_MAP.put("dxf", "application/dxf");
		MIME_TYPE_MAP.put("dxr", "application/x-director");
		MIME_TYPE_MAP.put("eps", "application/postscript");
		MIME_TYPE_MAP.put("etx", "text/x-setext");
		MIME_TYPE_MAP.put("exe", "application/octet-stream");
		MIME_TYPE_MAP.put("ez", "application/andrew-inset");
		MIME_TYPE_MAP.put("f", "text/plain");
		MIME_TYPE_MAP.put("f90", "text/plain");
		MIME_TYPE_MAP.put("fli", "video/x-fli");
		MIME_TYPE_MAP.put("gif", "image/gif");
		MIME_TYPE_MAP.put("gtar", "application/x-gtar");
		MIME_TYPE_MAP.put("gz", "application/x-gzip");
		MIME_TYPE_MAP.put("h", "text/plain");
		MIME_TYPE_MAP.put("hdf", "application/x-hdf");
		MIME_TYPE_MAP.put("hh", "text/plain");
		MIME_TYPE_MAP.put("hqx", "application/mac-binhex40");
		MIME_TYPE_MAP.put("htm", "text/html");
		MIME_TYPE_MAP.put("html", "text/html");
		MIME_TYPE_MAP.put("ice", "x-conference/x-cooltalk");
		MIME_TYPE_MAP.put("ief", "image/ief");
		MIME_TYPE_MAP.put("iges", "model/iges");
		MIME_TYPE_MAP.put("igs", "model/iges");
		MIME_TYPE_MAP.put("ips", "application/x-ipscript");
		MIME_TYPE_MAP.put("ipx", "application/x-ipix");
		MIME_TYPE_MAP.put("jpe", "image/jpeg");
		MIME_TYPE_MAP.put("jpeg", "image/jpeg");
		MIME_TYPE_MAP.put("jpg", "image/jpeg");
		MIME_TYPE_MAP.put("js", "application/x-javascript");
		MIME_TYPE_MAP.put("kar", "audio/midi");
		MIME_TYPE_MAP.put("latex", "application/x-latex");
		MIME_TYPE_MAP.put("lha", "application/octet-stream");
		MIME_TYPE_MAP.put("lsp", "application/x-lisp");
		MIME_TYPE_MAP.put("lzh", "application/octet-stream");
		MIME_TYPE_MAP.put("m", "text/plain");
		MIME_TYPE_MAP.put("man", "application/x-troff-man");
		MIME_TYPE_MAP.put("me", "application/x-troff-me");
		MIME_TYPE_MAP.put("mesh", "model/mesh");
		MIME_TYPE_MAP.put("mid", "audio/midi");
		MIME_TYPE_MAP.put("midi", "audio/midi");
		MIME_TYPE_MAP.put("mif", "application/vnd.mif");
		MIME_TYPE_MAP.put("mime", "www/mime");
		MIME_TYPE_MAP.put("mov", "video/quicktime");
		MIME_TYPE_MAP.put("movie", "video/x-sgi-movie");
		MIME_TYPE_MAP.put("mp2", "audio/mpeg");
		MIME_TYPE_MAP.put("mp3", "audio/mpeg");
		MIME_TYPE_MAP.put("mpe", "video/mpeg");
		MIME_TYPE_MAP.put("mpeg", "video/mpeg");
		MIME_TYPE_MAP.put("mpg", "video/mpeg");
		MIME_TYPE_MAP.put("mpga", "audio/mpeg");
		MIME_TYPE_MAP.put("ms", "application/x-troff-ms");
		MIME_TYPE_MAP.put("msh", "model/mesh");
		MIME_TYPE_MAP.put("nc", "application/x-netcdf");
		MIME_TYPE_MAP.put("oda", "application/oda");
		MIME_TYPE_MAP.put("pbm", "image/x-portable-bitmap");
		MIME_TYPE_MAP.put("pdb", "chemical/x-pdb");
		MIME_TYPE_MAP.put("pdf", "application/pdf");
		MIME_TYPE_MAP.put("pgm", "image/x-portable-graymap");
		MIME_TYPE_MAP.put("pgn", "application/x-chess-pgn");
		MIME_TYPE_MAP.put("png", "image/png");
		MIME_TYPE_MAP.put("pnm", "image/x-portable-anymap");
		MIME_TYPE_MAP.put("pot", "application/mspowerpoint");
		MIME_TYPE_MAP.put("ppm", "image/x-portable-pixmap");
		MIME_TYPE_MAP.put("pps", "application/mspowerpoint");
		MIME_TYPE_MAP.put("ppt", "application/mspowerpoint");
		MIME_TYPE_MAP.put("pptx", "application/mspowerpoint");
		MIME_TYPE_MAP.put("ppz", "application/mspowerpoint");
		MIME_TYPE_MAP.put("pre", "application/x-freelance");
		MIME_TYPE_MAP.put("prt", "application/pro_eng");
		MIME_TYPE_MAP.put("ps", "application/postscript");
		MIME_TYPE_MAP.put("qt", "video/quicktime");
		MIME_TYPE_MAP.put("ra", "audio/x-realaudio");
		MIME_TYPE_MAP.put("ram", "audio/x-pn-realaudio");
		MIME_TYPE_MAP.put("ras", "image/cmu-raster");
		MIME_TYPE_MAP.put("rgb", "image/x-rgb");
		MIME_TYPE_MAP.put("rm", "audio/x-pn-realaudio");
		MIME_TYPE_MAP.put("roff", "application/x-troff");
		MIME_TYPE_MAP.put("rpm", "audio/x-pn-realaudio-plugin");
		MIME_TYPE_MAP.put("rtf", "text/rtf");
		MIME_TYPE_MAP.put("rtx", "text/richtext");
		MIME_TYPE_MAP.put("scm", "application/x-lotusscreencam");
		MIME_TYPE_MAP.put("set", "application/set");
		MIME_TYPE_MAP.put("sgm", "text/sgml");
		MIME_TYPE_MAP.put("sgml", "text/sgml");
		MIME_TYPE_MAP.put("sh", "application/x-sh");
		MIME_TYPE_MAP.put("shar", "application/x-shar");
		MIME_TYPE_MAP.put("silo", "model/mesh");
		MIME_TYPE_MAP.put("sit", "application/x-stuffit");
		MIME_TYPE_MAP.put("skd", "application/x-koan");
		MIME_TYPE_MAP.put("skm", "application/x-koan");
		MIME_TYPE_MAP.put("skp", "application/x-koan");
		MIME_TYPE_MAP.put("skt", "application/x-koan");
		MIME_TYPE_MAP.put("smi", "application/smil");
		MIME_TYPE_MAP.put("smil", "application/smil");
		MIME_TYPE_MAP.put("snd", "audio/basic");
		MIME_TYPE_MAP.put("sol", "application/solids");
		MIME_TYPE_MAP.put("spl", "application/x-futuresplash");
		MIME_TYPE_MAP.put("src", "application/x-wais-source");
		MIME_TYPE_MAP.put("step", "application/STEP");
		MIME_TYPE_MAP.put("stl", "application/SLA");
		MIME_TYPE_MAP.put("stp", "application/STEP");
		MIME_TYPE_MAP.put("sv4cpio", "application/x-sv4cpio");
		MIME_TYPE_MAP.put("sv4crc", "application/x-sv4crc");
		MIME_TYPE_MAP.put("swf", "application/x-shockwave-flash");
		MIME_TYPE_MAP.put("t", "application/x-troff");
		MIME_TYPE_MAP.put("tar", "application/x-tar");
		MIME_TYPE_MAP.put("tcl", "application/x-tcl");
		MIME_TYPE_MAP.put("tex", "application/x-tex");
		MIME_TYPE_MAP.put("texi", "application/x-texinfo");
		MIME_TYPE_MAP.put("texinfo", "application/x-texinfo");
		MIME_TYPE_MAP.put("tif", "image/tiff");
		MIME_TYPE_MAP.put("tiff", "image/tiff");
		MIME_TYPE_MAP.put("tr", "application/x-troff");
		MIME_TYPE_MAP.put("tsi", "audio/TSP-audio");
		MIME_TYPE_MAP.put("tsp", "application/dsptype");
		MIME_TYPE_MAP.put("tsv", "text/tab-separated-values");
		MIME_TYPE_MAP.put("txt", "text/plain");
		MIME_TYPE_MAP.put("unv", "application/i-deas");
		MIME_TYPE_MAP.put("ustar", "application/x-ustar");
		MIME_TYPE_MAP.put("vcd", "application/x-cdlink");
		MIME_TYPE_MAP.put("vda", "application/vda");
		MIME_TYPE_MAP.put("viv", "video/vnd.vivo");
		MIME_TYPE_MAP.put("vivo", "video/vnd.vivo");
		MIME_TYPE_MAP.put("vrml", "model/vrml");
		MIME_TYPE_MAP.put("wav", "audio/x-wav");
		MIME_TYPE_MAP.put("wrl", "model/vrml");
		MIME_TYPE_MAP.put("xbm", "image/x-xbitmap");
		MIME_TYPE_MAP.put("xlc", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xll", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xlm", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xlw", "application/vnd.ms-excel");
		MIME_TYPE_MAP.put("xml", "text/xml");
		MIME_TYPE_MAP.put("xpm", "image/x-xpixmap");
		MIME_TYPE_MAP.put("xwd", "image/x-xwindowdump");
		MIME_TYPE_MAP.put("xyz", "chemical/x-pdb");
		MIME_TYPE_MAP.put("zip", "application/zip");
		MIME_TYPE_MAP.put("rar", "application/rar");

        // Added By Kuchi Vamsi

        EXT_CAT_MAP.put("jpg",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("jpeg",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("jpe",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("tiff",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("tif",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("png",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("bmp",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("gif",CATEGORY_IMAGE);
        EXT_CAT_MAP.put("zip",CATEGORY_ZIP);
        EXT_CAT_MAP.put("rar",CATEGORY_RAR);
        EXT_CAT_MAP.put("csv",CATEGORY_CSV);

        // Till here By Kuchi Vamsi
    }

	/* image types */
	/* range 0 - 99 */
	public static final FileType JPEG = new FileType(1, CATEGORY_IMAGE, "jpg");
	public static final FileType PNG = new FileType(2, CATEGORY_IMAGE, "png");
	public static final FileType TIF = new FileType(3, CATEGORY_IMAGE, "tif");
	public static final FileType GIF = new FileType(4, CATEGORY_IMAGE, "gif");
	public static final FileType BMP = new FileType(5, CATEGORY_IMAGE, "bmp");
	public static final FileType JPEG_ALT_1 = new FileType(6, CATEGORY_IMAGE,
			"jpeg");

	/* audio types */
	/* range 100-199 */
	public static final FileType AUDIO_MP3 = new FileType(100, CATEGORY_AUDIO,
			"mp3");
	public static final FileType AUDIO_WAV = new FileType(101, CATEGORY_AUDIO,
			"wav");

	/* text types */
	/* range 200 -299 */
	public static final FileType PDF = new FileType(201, CATEGORY_TEXT, "pdf");
	public static final FileType HTML = new FileType(202, CATEGORY_TEXT, "html");
	public static final FileType PLAIN_TEXT = new FileType(203, CATEGORY_TEXT,
			"txt");
	public static final FileType RTF = new FileType(204, CATEGORY_TEXT, "rtf");
	public static final FileType CSV = new FileType(205, CATEGORY_TEXT, "csv");
	// the glut of microsoft types...
	public static final FileType MS_WORD_DOC = new FileType(250, CATEGORY_TEXT,
			"doc");
	public static final FileType MS_WORD_PPT = new FileType(251, CATEGORY_TEXT,
			"ppt");
	public static final FileType MS_WORD_XLS = new FileType(252, CATEGORY_TEXT,
			"xls");

	/* video types */
	/* range 300 -399 */
	public static final FileType VIDEO_MPG = new FileType(300, CATEGORY_VIDEO,
			"mpg");
	public static final FileType VIDEO_WMV = new FileType(301, CATEGORY_VIDEO,
			"wmv");
	public static final FileType VIDEO_FLV = new FileType(302, CATEGORY_VIDEO,
			"flv");
	public static final FileType VIDEO_MP4 = new FileType(303, CATEGORY_VIDEO,
			"mp4");
	public static final FileType VIDEO_AVI = new FileType(304, CATEGORY_VIDEO,
			"avi");
	public static final FileType VIDEO_QUICKTIME = new FileType(305,
			CATEGORY_VIDEO, "mov");
	public static final FileType VIDEO_MPEG = new FileType(306, CATEGORY_VIDEO,
			"mpeg");
	public static final FileType VIDEO_RTMP = new FileType(307, CATEGORY_VIDEO,
			"rtmp");
	public static final FileType VIDEO_H264 = new FileType(308, CATEGORY_VIDEO,
			H264_FORMAT);

	/* others types */
	/* 400 - 499 */
	public static final FileType MALICIOUS = new FileType(400,
			CATEGORY_EXECUTABLE, "exe");
	public static final FileType UNKNOWN = new FileType(401, CATEGORY_UNKNOWN,
			"Unknown");
	public static final FileType FLASH = new FileType(200, CATEGORY_FLASH,
			"swf");
	public static final FileType ZIP = new FileType(402, CATEGORY_UNKNOWN,
			"zip");
    public static final FileType RAR = new FileType(403, CATEGORY_UNKNOWN,
			"rar");

	private int category;
	private String extension;
	private String mimeType;
	private int typeId;
	private String name;

	public FileType() {
	}

	public static FileType getInstance() {
		return new FileType();
	}

	/**
	 * gets an integer representing the category of the file. See categories
	 * 
	 * @return
	 */
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public static FileType[] getFileTypesByCategory(int category) {
		List<FileType> outputList = new ArrayList<FileType>();
		for (Iterator<FileType> x = map.values().iterator(); x.hasNext();) {
			FileType type = x.next();
			if (type.getCategory() == category) {
				outputList.add(type);
			}
		}
		FileType[] output = new FileType[outputList.size()];
		outputList.toArray(output);

		return output;
	}

	/**
	 * gets an integer representing the specific type of the file
	 * 
	 * @return
	 */
	public int getFileTypeCode() {
		return getTypeId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the FileType object given the corresponding typeId
	 * 
	 * @param fileTypeId
	 * @return
	 */
	public static FileType getFileType(int fileTypeId) {
		return map.get(fileTypeId);
	}

	public List<FileType> getTypes() {
		List<FileType> output = new ArrayList<FileType>();
		output.addAll(map.values());
		return output;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	FileType(int id, int category, String extension) {
		setTypeId(id);
		this.category = category;
		this.extension = extension;
		String name = extension + " ";
		switch (category) {
		case CATEGORY_IMAGE:
			name += "Image";
			break;
		case CATEGORY_TEXT:
			name += "Text";
			break;
		case CATEGORY_AUDIO:
			name += "Audio";
			break;
		case CATEGORY_VIDEO:
			name += "Video";
			break;
		}
		this.setName(name);
		map.put(id, this);
		this.mimeType = MIME_TYPE_MAP.get(extension);
		if (mimeType == null) {
			mimeType = "application/unknown";
		}
	}

	public FileType getPreferredFileType(String mimeType) {
		List<FileType> possibleTypes = new ArrayList<FileType>();
		for (Iterator<FileType> x = map.values().iterator(); x.hasNext();) {
			FileType type = x.next();
			String mimeTypeForFileType = type.getMimeHeader();

			if (mimeTypeForFileType.equals(mimeType)) {
				possibleTypes.add(type);
			}
		}

		if (possibleTypes.size() > 0) {
			// @TODO: update this logic
			if (possibleTypes.size() > 1) {
//				System.out.println("more than one possible type returned, using first response");
//				for (int i = 0; i < possibleTypes.size(); i++) {
//					System.out.println(possibleTypes.get(i).getName());
//				}
			}
			return possibleTypes.get(0);
		}
		return null;
	}

	public int getCategoryCode() {
		return category;
	}

	public String getExtension() {
		return extension;
	}

	public boolean equals(Object o) {
		if (o instanceof FileType) {
			FileType ft = (FileType) o;
			if (ft.getCategory() == category
					&& ft.getFileTypeCode() == getTypeId()) {
				return true;
			}
		}
		return false;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public static String getCategoryDescriptionString(int category) {
		switch (category) {
		case FileType.CATEGORY_AUDIO: {
			return "Audio";
		}
		case FileType.CATEGORY_IMAGE: {
			return "Image";
		}
		case FileType.CATEGORY_TEXT: {
			return "Text";
		}
		case FileType.CATEGORY_THUMBNAIL: {
			return "Thumbnail Image";
		}
		case FileType.CATEGORY_UNKNOWN:
		default: {
			return "Unknown";
		}
		}
	}

	protected String constructIdentifier(Class<?> className, int typeId) {
		return className.getSimpleName() + ":" + typeId;
	}

	public String getIdentifier() {
		return constructIdentifier(FileType.class, getTypeId());
	}

	public int getTypeId() {
		return typeId;
	}

	public String getMimeHeader() {
		return mimeType;
	}

	public static String getMimeType(String extension) {
		return MIME_TYPE_MAP.get(extension);
	}
    // Kuchi changed this code , used the Apache utils
	public static String getExtensionFromFileName(String fileName) {
       return FilenameUtils.getExtension(fileName);
	}
    // Kuchi added this method to get the Category based on extn
    public static int getCategory(String extn){
       return  EXT_CAT_MAP.get(extn.toLowerCase());
        
    }
}