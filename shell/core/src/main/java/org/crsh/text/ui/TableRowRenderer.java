/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.crsh.text.ui;

import org.crsh.text.LineReader;
import org.crsh.text.Renderer;

import java.util.List;

class TableRowRenderer extends Renderer {

  /** . */
  final RowRenderer row;

  /** . */
  private final boolean header;

  /** . */
  private TableRowRenderer previous;

  /** . */
  private TableRowRenderer next;

  /** . */
  private int index;

  TableRowRenderer(RowRenderer row, boolean header) {

    //
    this.row = row;
    this.header = header;
    this.index = 0;
  }

  TableRowRenderer add(TableRowRenderer next) {
    next.previous = this;
    next.index = index + 1;
    this.next = next;
    return next;
  }

  boolean hasTop() {
    return header && previous != null;
  }

  boolean hasBottom() {
    return header && next != null && !next.header;
  }

  int getIndex() {
    return index;
  }

  int getSize() {
    return index + 1;
  }

  TableRowRenderer previous() {
    return previous;
  }

  TableRowRenderer next() {
    return next;
  }

  public boolean isHeader() {
    return header;
  }

  int getColsSize() {
    return row.getSize();
  }

  public List<Renderer> getCols() {
    return row.getCols();
  }

  @Override
  public int getActualWidth() {
    return row.getActualWidth();
  }

  @Override
  public int getMinWidth() {
    return row.getMinWidth();
  }

  @Override
  public int getActualHeight(int width) {
    int actualHeight = row.getActualHeight(width);
    if (hasTop()) {
      actualHeight++;
    }
    if (hasBottom()) {
      actualHeight++;
    }
    return actualHeight;
  }

  @Override
  public int getMinHeight(int width) {
    int minHeight = row.getMinHeight(width);
    if (hasTop()) {
      minHeight++;
    }
    if (hasBottom()) {
      minHeight++;
    }
    return minHeight;
  }

  TableRowReader renderer(int[] widths, BorderStyle separator, int height) {
    return new TableRowReader(row, widths, separator, header, height);
  }

  @Override
  public LineReader reader(int width) {
    return row.reader(width);
  }
}
