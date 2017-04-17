.atwho-view {
  position:absolute;
  top: 0;
  left: 0;
  display: none;
  margin-top: 18px;
  background: white;
  color: black;
  border: 1px solid #DDD;
  border-radius: 3px;
  box-shadow: 0 0 5px rgba(0,0,0,0.1);
  min-width: 120px;
  z-index: 11110 !important;
}

.atwho-view .atwho-header {
  padding: 5px;
  margin: 5px;
  cursor: pointer;
  border-bottom: solid 1px #eaeff1;
  color: #6f8092;
  font-size: 11px;
  font-weight: bold;
}

.atwho-view .atwho-header .small {
  color: #6f8092;
  float: right;
  padding-top: 2px;
  margin-right: -5px;
  font-size: 12px;
  font-weight: normal;
}

.atwho-view .atwho-header:hover {
  cursor: default;
}

.atwho-view .cur {
  background: #3366FF;
  color: white;
}
.atwho-view .cur small {
  color: white;
}
.atwho-view strong {
  color: #3366FF;
}
.atwho-view .cur strong {
  color: white;
  font:bold;
}
.atwho-view ul {
  /* width: 100px; */
  list-style:none;
  padding:0;
  margin:auto;
  max-height: 200px;
  overflow-y: auto;
}
.atwho-view ul li {
  display: block;
  padding: 5px 10px;
  border-bottom: 1px solid #DDD;
  cursor: pointer;
  /* border-top: 1px solid #C8C8C8; */
}
.atwho-view small {
  font-size: smaller;
  color: #777;
  font-weight: normal;
}