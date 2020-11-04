type
  [...]
  deltaT = -1..1;
  labT = array[1..23,1..31] of boolean;
  dirT = (no, up, right, down, left);
  [...]

var
  [...]
  lab : labT;
  catDir : dirT;
  mx, my, kx, ky : byte;
  [...]

[...]

procedure MoveCat;
var
  dirDataPos, newDirDataPos, ways : word;
  col, line : byte;
  newDir, dir : array[1..100] of dirT;
  wayX, wayY, newWayX, newWayY : array[1..100] of byte;
  p : array[1..31,1..23] of boolean;

procedure FirstMoveTest(dx,dy:deltaT;dir2:dirT);
begin
  if (kx+dx<>mx) or (ky+dy<>my) then begin
    if not lab[ky+dy,kx+dx] then begin
      Inc(dirDataPos);
      wayX[dirDataPos]:=kx+2*dx;
      wayY[dirDataPos]:=ky+2*dy;
      dir[dirDataPos]:=dir2;
      p[kx+2*dx,ky+2*dy]:=true;
    end;
  end else catDir:=dir2;
end;

procedure SecondMoveTest(dx,dy:deltaT);
begin
  if (wayX[dirDataPos]+dx<>mx) or (wayY[dirDataPos]+dy<>my) then begin
    if not(lab[wayY[dirDataPos]+dy,wayX[dirDataPos]+dx])
        and not(p[wayX[dirDataPos]+2*dx,wayY[dirDataPos]+2*dy]) then begin
      Inc(newDirDataPos);
      newWayX[newDirDataPos]:=wayX[dirDataPos]+2*dx;
      newWayY[newDirDataPos]:=wayY[dirDataPos]+2*dy;
      newDir[newDirDataPos]:=dir[dirDataPos];
      p[wayX[dirDataPos]+2*dx,wayY[dirDataPos]+2*dy]:=true;
    end;
  end else catDir:=dir[dirDataPos];
end;

begin
  if (kx mod 2=0) and (ky mod 2=0) then begin
    for col:=1 to 31 do for line:=1 to 23 do p[col,line]:=false;
    dirDataPos:=0; catDir:=no;
    p[kx,ky]:=true;
    FirstMoveTest(1,0,right);
    FirstMoveTest(-1,0,left);
    FirstMoveTest(0,1,down);
    FirstMoveTest(0,-1,up);
    ways:=dirDataPos;
    for dirDataPos:=1 to ways do
      if (wayX[dirDataPos]=mx) and (wayY[dirDataPos]=my)
        then catDir:=dir[dirDataPos];
    while (catDir=no) and ((mx<>kx) or (my<>ky)) do begin
      newDirDataPos:=0;
      for dirDataPos:=1 to ways do begin
        SecondMoveTest(1,0);
        SecondMoveTest(-1,0);
        SecondMoveTest(0,1);
        SecondMoveTest(0,-1);
      end;
      ways:=newDirDataPos;
      for dirDataPos:=1 to newDirDataPos do begin
        wayX[dirDataPos]:=newWayX[dirDataPos];
        wayY[dirDataPos]:=newWayY[dirDataPos];
        dir[dirDataPos]:=newDir[dirDataPos];
        if (wayX[dirDataPos]=mx) and (wayY[dirDataPos]=my)
          then catDir:=dir[dirDataPos];
      end;
    end;
  end;
  DeleteCat;
  [...]
  case catDir of
    left: Dec(kx,1);
    down: Inc(ky,1);
    right: Inc(kx,1);
    up: Dec(ky,1);
  end;
  [...]
  DrawCat;
end;
