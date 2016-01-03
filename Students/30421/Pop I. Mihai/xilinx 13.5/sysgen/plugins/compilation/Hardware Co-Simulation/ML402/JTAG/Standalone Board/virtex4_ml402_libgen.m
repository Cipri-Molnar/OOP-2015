%
% Filename:    virtex4_ml402_libgen.m
%
% Description: Creates a library containing all of the non-
%              memory mapped ports for the
%              Virtex4 ML402
%

function virtex4_ml402_libgen()

disp('Loading the ''xbsTypes_r4'' library...');
load_system('xbsTypes_r4');

lib = ['virtex4_ml402_lib'];

disp(['Creating the ''' lib ''' library...']);
new_system(lib, 'Library');
open_system(lib);

name = 'LEDs';
width = 4;
ss = add_output(lib, name, width);
set_param(ss, 'Position', [220 20 300 50]);

name = 'RXD';
width = 1;
ss = add_input(lib, name, width);
set_param(ss, 'Position', [20 20 100 50]);

name = 'TXD';
width = 1;
ss = add_output(lib, name, width);
set_param(ss, 'Position', [220 70 300 100]);

name = 'Reset';
width = 1;
ss = add_input(lib, name, width);
set_param(ss, 'Position', [20 70 100 100]);

return;

function ss = add_input(system, portname, width)
disp(['Creating the ''' portname ''' input port...']);
ss = [system, '/' portname];
const = [ss '/Constant'];
port = [ss '/' portname];
out = [ss '/OutPort'];

% create the SubSystem
add_block('built-in/SubSystem', ss);

% create the internal components
add_block('built-in/Constant', const);
add_block('built-in/OutPort', out);

% create the actual port
add_block('xbsTypes_r4/Gateway In', port);
xlSetNonMemMap(port, 'Xilinx', 'jtaghwcosim');

% connect the internal components
add_line(ss, 'Constant/1', [portname '/1']);
add_line(ss, [portname '/1'], 'OutPort/1');

% configure the SubSystem
set_param(ss, 'ShowName', 'off');
set_param(ss, 'CopyFcn', 'set_param(gcb,''LinkStatus'',''inactive'');');
set_param(ss, 'Mask', 'on');
set_param(ss, 'MaskType', 'Non-Memory-Mapped Input Port');
set_param(ss, 'MaskSelfModifiable', 'on');
set_param(ss, 'MaskVariables', 'signed=@1;bpt=@2;show_in=@3;c=@4;');
set_param(ss, 'MaskPrompts', {'Signed  (2''s comp)';'Binary Point';'Provide Input Double';'Constant for Simulation'});
set_param(ss, 'MaskStyles', {'checkbox';'edit';'checkbox';'edit'});
set_param(ss, 'MaskValues', {'off';'0';'off';'0'});
callback = sprintf([ ...
    'mask_vis = get_param(gcb, ''MaskVisibilities'');\n' ...
    'if (strcmp(get_param(gcb, ''show_in''),''on''))\n' ...
    '    mask_vis(4) = {''off''};\n' ...
    'else\n' ...
    '    mask_vis(4) = {''on''};\n' ...
    'end\n' ...
    'set_param(gcb, ''MaskVisibilities'', mask_vis);\n' ...
    ]);
set_param(gcb, 'MaskCallbacks', {'';'';callback;''});
if (width == 1)
    set_param(ss, 'MaskVisibilities', {'off';'off';'on';'on'});
    set_param(ss, 'MaskDescription', sprintf([ ...
        'Port Name: "' portname '"\n' ...
        'Port Type: Boolean' ...
        ]));
    sign_test = '';
else
    set_param(ss, 'MaskDescription', sprintf([ ...
        'Port Name: "' portname '"\n' ...
        sprintf('Port Type: %d-bit Vector', width) ...
        ]));
    sign_test = sprintf([ ...
        'if (signed)\n' ...
        '    set_param([gcb ''/' portname '''],''arith_type'',''Signed  (2''''s comp)'');\n' ...
        'else\n' ...
        '    set_param([gcb ''/' portname '''],''arith_type'',''Unsigned'');\n' ...
        'end\n' ...
        ]);
end
set_param(ss, 'MaskDisplay', sprintf([ ...
    'patch([0 iWidth iWidth 0],[0 0 iHeight iHeight] , bg);\n' ...
    'patch(logoX,logoY, fg);\n' ...
    'plot([0 0 iWidth iWidth 0], [0 iHeight iHeight 0 0]);\n' ...
    'port_label(portdir,1,portstr);\n' ...
    'port_label(''output'',1,''' portname ''');\n' ...
    'text(2,5,''nmm'');\n' ...
    ]));
set_param(ss, 'MaskInitialization', sprintf([ ...
    '[bg,fg] = xlcmap(''IO'',0);\n' ...
    'iPos = get_param(gcb,''Position'');\n' ...
    'iWidth=iPos(3)-iPos(1);\n' ...
    'iHeight=iPos(4)-iPos(2);\n' ...
    '[logoX, logoY] = xlogo(iPos);\n' ...
    sign_test ...
    'if (show_in)\n' ...
    '    old = find_system(gcb, ''LookUnderMasks'', ''all'', ''Name'', ''Constant'');\n' ...
    '    if (length(old) > 0)\n' ...
    '        replace_block(gcb, ''Name'', ''Constant'', ''InPort'', ''noprompt'');\n' ...
    '        set_param([gcb ''/Constant''], ''Name'', ''InPort'');\n' ...
    '    end\n' ...
    '    portdir = ''input'';\n' ...
    '    portstr = ''dbl'';\n' ...
    'else\n' ...
    '    old = find_system(gcb, ''LookUnderMasks'', ''all'', ''Name'', ''InPort'');\n' ...
    '    if (length(old) > 0)\n' ...
    '        replace_block(gcb, ''Name'', ''InPort'', ''Constant'', ''noprompt'');\n' ...
    '        set_param([gcb ''/InPort''], ''Name'', ''Constant'');\n' ...
    '        set_param([gcb ''/Constant''], ''Value'', ''c'');\n' ...
    '    end\n' ...
    '    portdir = ''output'';\n' ...
    '    portstr = '''';\n' ...
    'end\n' ...
    ]));
set_param(ss, 'MaskIconFrame', 'off');

% configure the Constant
set_param(const, 'Position', [100 100 120 120]);
set_param(const, 'Value', 'c');

% configure the Gateway In
set_param(port, 'Position', [180 99 235 121]);
if (width == 1)
    set_param(port, 'arith_type', 'Boolean');
else
    set_param(port, 'arith_type', 'Unsigned');
    set_param(port, 'bin_pt', sprintf('max(0,min(bpt,%d))',width));
    set_param(port, 'n_bits', sprintf('%d', width));
end

% configure the OutPort
set_param(out, 'Position', [295 100 315 120]);

return;

function ss = add_output(system, portname, width)
disp(['Creating the ''' portname ''' output port...']);
ss = [system, '/' portname];
in = [ss '/InPort'];
force = [ss '/Reinterpret'];
conv = [ss '/Convert'];
port = [ss '/' portname];
term = [ss '/Terminator'];

% create the SubSystem
add_block('built-in/SubSystem', ss);

% create the internal components
add_block('built-in/InPort', in);
if (width > 1)
    add_block('xbsTypes_r4/Reinterpret', force);
end
add_block('xbsTypes_r4/Convert', conv);
add_block('built-in/Terminator', term);

% create the actual port
add_block('xbsTypes_r4/Gateway Out', port);
xlSetNonMemMap(port, 'Xilinx', 'jtaghwcosim');

% connect the internal components
if (width > 1)
    add_line(ss, 'InPort/1', 'Reinterpret/1');
    add_line(ss, 'Reinterpret/1', 'Convert/1');
else
    add_line(ss, 'InPort/1', 'Convert/1');
end
add_line(ss, 'Convert/1', [portname '/1']);
add_line(ss, [portname '/1'], 'Terminator/1');

% configure the SubSystem
set_param(ss, 'ShowName', 'off');
set_param(ss, 'CopyFcn', 'set_param(gcb,''LinkStatus'',''inactive'');');
set_param(ss, 'Mask', 'on');
set_param(ss, 'MaskType', 'Non-Memory-Mapped Output Port');
set_param(ss, 'MaskSelfModifiable', 'on');
set_param(ss, 'MaskVariables', 'show_out=@1;');
set_param(ss, 'MaskPrompts', {'Provide Output Double'});
set_param(ss, 'MaskStyles', {'checkbox'});
set_param(ss, 'MaskValues', {'off'});
if (width == 1)
    set_param(ss, 'MaskDescription', sprintf([ ...
        'Port Name: "' portname '"\n' ...
        'Port Type: Boolean' ...
        ]));
else
    set_param(ss, 'MaskDescription', sprintf([ ...
        'Port Name: "' portname '"\n' ...
        sprintf('Port Type: %d-bit Vector', width) ...
        ]));
end
set_param(ss, 'MaskDisplay', sprintf([ ...
    'patch([0 iWidth iWidth 0],[0 0 iHeight iHeight] , bg);\n' ...
    'patch(logoX,logoY, fg);\n' ...
    'plot([0 0 iWidth iWidth 0], [0 iHeight iHeight 0 0]);\n' ...
    'port_label(portdir,1,portstr);\n' ...
    'port_label(''input'',1,''' portname ''');\n' ...
    'text(iWidth-7,5,''nmm'');\n' ...
    ]));
set_param(ss, 'MaskInitialization', sprintf([ ...
    '[bg,fg] = xlcmap(''IO'',0);\n' ...
    'iPos = get_param(gcb,''Position'');\n' ...
    'iWidth=iPos(3)-iPos(1);\n' ...
    'iHeight=iPos(4)-iPos(2);\n' ...
    '[logoX, logoY] = xlogo(iPos);\n' ...
    'if (show_out)\n' ...
    '    old = find_system(gcb, ''LookUnderMasks'', ''all'', ''Name'', ''Terminator'');\n' ...
    '    if (length(old) > 0)\n' ...
    '        replace_block(gcb, ''Name'', ''Terminator'', ''OutPort'', ''noprompt'');\n' ...
    '        set_param([gcb ''/Terminator''], ''Name'', ''OutPort'');\n' ...
    '    end\n' ...
    '    portdir = ''output'';\n' ...
    '    portstr = ''dbl'';\n' ...
    'else\n' ...
    '    old = find_system(gcb, ''LookUnderMasks'', ''all'', ''name'', ''OutPort'');\n' ...
    '    if (length(old) > 0)\n' ...
    '        replace_block(gcb, ''Name'', ''OutPort'', ''Terminator'', ''noprompt'');\n' ...
    '        set_param([gcb ''/OutPort''], ''Name'', ''Terminator'');\n' ...
    '    end\n' ...
    '    portdir = ''input'';\n' ...
    '    portstr = '''';\n' ...
    'end\n' ...
    ]));
set_param(ss, 'MaskIconFrame', 'off');

if (width > 1)
    % configure the InPort
    set_param(in, 'Position', [50 100 70 120]);

    % configure the Reinterpret
    set_param(force, 'Position', [130 94 170 126]);
else
    % configure the InPort
    set_param(in, 'Position', [150 100 170 120]);
end

% configure the Convert
set_param(conv, 'Position', [230 95 275 125]);
if (width == 1)
    set_param(conv, 'arith_type', 'Boolean');
else
    set_param(conv, 'arith_type', 'Unsigned');
    set_param(conv, 'bin_pt', sprintf('0',width));
    set_param(conv, 'n_bits', sprintf('%d', width));
end

% configure the Gateway Out
set_param(port, 'Position', [335 99 390 121]);

% configure the Terminator
set_param(term, 'Position', [450 100 470 120]);

return;
