%
% Filename:    xltarget.m
%
% Description: Defines the target compilation entry points

function s = xltarget
  s = {
     struct('name', 'SP601 (JTAG)', ...
            'target_info', 'xlHwcosimTarget(''sp601-jtag'')', ...
            'title', 'JTAG'), ...
     struct('name', 'SP601 (Point-to-point Ethernet)', ...
            'target_info', 'xlHwcosimTarget(''sp601-ppethernet'')', ...
            'title', 'Point-to-point Ethernet'), ...
  };
