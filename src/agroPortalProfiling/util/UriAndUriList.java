package agroPortalProfiling.util;

import java.util.ArrayList;
import java.util.Objects;

public class UriAndUriList {
	
	private String uri;
	private ArrayList<Uri> uriList;
	
	
	public UriAndUriList() {
	}
	
	public UriAndUriList(String uri, ArrayList<Uri> uriList) {
		this.uri = uri;
		this.uriList = uriList;
	}

	public ArrayList<Uri> getUriList() {
		return uriList;
	}

	public void setUriList(ArrayList<Uri> uriList) {
		this.uriList = uriList;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	// Méthode toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(uri).append(", [");
        for (int i = 0; i < uriList.size(); i++) {
            sb.append(uriList.get(i).toString());
            if (i < uriList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UriAndUriList that = (UriAndUriList) obj;
        return  Objects.equals(uriList, that.uriList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uriList);
    }

	
}
