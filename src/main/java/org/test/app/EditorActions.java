package org.test.app;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("editorActions")
@Scope("session")
public class EditorActions {
  private String text;

  public EditorActions() {

  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
