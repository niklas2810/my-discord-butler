/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Marc R. Hoffmann - initial API and implementation
 *
 *******************************************************************************/

 /*****************************
 * This file originated in the Jacoco project itself.
 * https://github.com/jacoco/jacoco/blob/master/org.jacoco.report/src/org/jacoco
 * /report/internal/html/resources/sort.js
 *
 * This file was altered by Niklas Arndt (https://github.com/niklas2810) to create a different
 * look-and-feel. I do not claim every part of this file as my own work.
 *
 * The original copyright license by Mountainminds GmbH & Co. KG and Contributors
 * will be included in all altered files, as well as this notices.
 *******************************/
(function () {

  /**
   * Sets the initial sorting derived from the hash.
   *
   * @param linkelementids
   *          list of element ids to search for links to add sort inidcator
   *          hash links
   */
  function initialSort(linkelementids) {
    loadScript("custom.js");

    window.linkelementids = linkelementids;
    var hash = window.location.hash;
    if (hash) {
      var m = hash.match(/up-./);
      if (m) {
        var header = window.document.getElementById(m[0].charAt(3));
        if (header) {
          sortColumn(header, true);
        }
        return;
      }
      var m = hash.match(/dn-./);
      if (m) {
        var header = window.document.getElementById(m[0].charAt(3));
        if (header) {
          sortColumn(header, false);
        }
        return
      }
    }
  }

  /**
   * Load a JavaScript file from an external source.
   * @param {*} url The url of the script
   */
  function loadScript(url) {

    var resourceScript = document.createElement("script");
    resourceScript.innerHTML = `
    function endsWidth(str, suffix) {
        return str.match(suffix+"$")==suffix;
    }


    function getResourcePath() {
        String.prototype.endsWith = function(suffix) {
            return this.indexOf(suffix, this.length - suffix.length) !== -1;
        };
        var path = window.location.pathname;
        var parts = path.split("/");
        parts.pop();
        var last = parts.pop();
        if(last === undefined || last.split(".").length < 3) { //This should be the index site
          return path.replace("index.html", "").replace("jacoco-sessions.html", "")
          +"jacoco-resources/";
        }
        //Is in subfolder
        var parts = path.split("/");
        parts.pop();
        parts.pop();

        return parts.join("/")+"/jacoco-resources/";
   }

   function getResourcePathWithOrigin() {
        if(window.location.protocol === "file:") return "file://"+getResourcePath();
        return window.location.origin+getResourcePath();
   }
   `;
    document.body.appendChild(resourceScript);

    var script = document.createElement("script"); // create a script DOM node
    var loc = getResourcePath() + url;
    console.log("Location:", loc);
    script.src = loc; // set its src to the provided URL
    console.log("Source path: ", script.src);

    document.body.appendChild(script);
  }

  /**
   * Sorts the columns with the given header dependening on the current sort state.
   */
  function toggleSort(header) {
    var sortup = header.className.indexOf('down ') == 0;
    sortColumn(header, sortup);
  }

  /**
   * Sorts the columns with the given header in the given direction.
   */
  function sortColumn(header, sortup) {
    var table = header.parentNode.parentNode.parentNode;
    var body = table.tBodies[0];
    var colidx = getNodePosition(header);

    resetSortedStyle(table);

    var rows = body.rows;
    var sortedrows = [];
    for (var i = 0; i < rows.length; i++) {
      r = rows[i];
      sortedrows[parseInt(r.childNodes[colidx].id.slice(1))] = r;
    }

    var hash;

    if (sortup) {
      for (var i = sortedrows.length - 1; i >= 0; i--) {
        body.appendChild(sortedrows[i]);
      }
      header.className = 'up ' + header.className;
      hash = 'up-' + header.id;
    } else {
      for (var i = 0; i < sortedrows.length; i++) {
        body.appendChild(sortedrows[i]);
      }
      header.className = 'down ' + header.className;
      hash = 'dn-' + header.id;
    }

    setHash(hash);
  }

  /**
   * Adds the sort indicator as a hash to the document URL and all links.
   */
  function setHash(hash) {
    window.document.location.hash = hash;
    ids = window.linkelementids;
    for (var i = 0; i < ids.length; i++) {
      setHashOnAllLinks(document.getElementById(ids[i]), hash);
    }
  }

  /**
   * Extend all links within the given tag with the given hash.
   */
  function setHashOnAllLinks(tag, hash) {
    links = tag.getElementsByTagName("a");
    for (var i = 0; i < links.length; i++) {
      var a = links[i];
      var href = a.href;
      var hashpos = href.indexOf("#");
      if (hashpos != -1) {
        href = href.substring(0, hashpos);
      }
      a.href = href + "#" + hash;
    }
  }

  /**
   * Calculates the position of a element within its parent.
   */
  function getNodePosition(element) {
    var pos = -1;
    while (element) {
      element = element.previousSibling;
      pos++;
    }
    return pos;
  }

  /**
   * Remove the sorting indicator style from all headers.
   */
  function resetSortedStyle(table) {
    for (var c = table.tHead.firstChild.firstChild; c; c = c.nextSibling) {
      if (c.className) {
        if (c.className.indexOf('down ') == 0) {
          c.className = c.className.slice(5);
        }
        if (c.className.indexOf('up ') == 0) {
          c.className = c.className.slice(3);
        }
      }
    }
  }

  window['initialSort'] = initialSort;
  window['toggleSort'] = toggleSort;

})();