// Copyright (C) 2003-2009 by Object Mentor, Inc. All rights reserved.
// Released under the terms of the CPL Common Public License version 1.0.
package fitnesse.responders.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.FileUtil;
import fitnesse.FitNesseContext;
import fitnesse.http.MockRequest;
import fitnesse.http.Response;
import fitnesse.testutil.FitNesseUtil;

public class CreateDirectoryResponderTest {
  private FitNesseContext context;

  @Before
  public void setUp() throws Exception {
    context = FitNesseUtil.makeTestContext(null);
    FileUtil.makeDir(context.getRootPagePath());
    FileUtil.makeDir(context.getRootPagePath() + "/files");
  }

  @After
  public void tearDown() throws Exception {
    FileUtil.deleteFileSystemDirectory(context.getRootPagePath());
  }

  @Test
  public void testMakeResponse() throws Exception {
    CreateDirectoryResponder responder = new CreateDirectoryResponder();
    MockRequest request = new MockRequest();
    request.addInput("dirname", "subdir");
    request.setResource("");

    Response response = responder.makeResponse(context, request);

    File file = new File(context.getRootPagePath() + "/subdir");
    assertTrue(file.exists());
    assertTrue(file.isDirectory());

    assertEquals(303, response.getStatus());
    assertEquals("/", response.getHeader("Location"));
  }
}
