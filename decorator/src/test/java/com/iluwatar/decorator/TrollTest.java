/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.decorator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/7/15 - 7:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class TrollTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender(Troll.class);
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  @Test
  public void testTrollActions() throws Exception {
    final Troll troll = new Troll();
    assertEquals(10, troll.getAttackPower());

    troll.attack();
    assertEquals("The troll swings at you with a club!", appender.getLastMessage());

    troll.fleeBattle();
    assertEquals("The troll shrieks in horror and runs away!", appender.getLastMessage());

    assertEquals(2, appender.getLogSize());
  }

  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {

    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender(Class clazz) {
      ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public String getLastMessage() {
      return log.get(log.size() - 1).getMessage();
    }

    public int getLogSize() {
      return log.size();
    }
  }
}
