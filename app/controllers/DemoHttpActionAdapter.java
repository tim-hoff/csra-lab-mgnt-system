package controllers;

import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;

import static play.mvc.Results.*;

public class DemoHttpActionAdapter extends DefaultHttpActionAdapter {

    @Override
    public Object adapt(int code, WebContext context) {
            return super.adapt(code, context);
    }
}