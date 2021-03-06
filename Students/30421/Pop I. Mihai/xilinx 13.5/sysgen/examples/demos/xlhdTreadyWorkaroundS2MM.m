function [ tready, s2mm_tvalid ] = xlTreadyWorkaroundS2MM( ...
    s2mm_fsync_out,  s2mm_tready, tvalid , s2mm_tlast, aresetn, ...
    VSIZE...
    )
%
%xlTreadyWorkaroundS2MM is function written to transform tready produced by
%AXI_VDMA to be a true tready signal. This is required due to protocol
%violation of the AXI Slave Streaming Interface. The AXI_VDAM is not ready
%to accept data till the fsync_out signal of the S2MM is asserted by the
%AXI_VDMA Interface block. Hence the tready generated by this block gates
%the s2mm_tready of the VDMA Interface block until fsync_out == 1 is
%detected. Similalry it gates the user tvalid signal from propagating to
%the VDMA until fsync_out == 1 is detected. Also at the end of each frame
%and before the start of next frame user tvalid and s2mm_tready are gated
%till the next fsync == 1 is detected. This will be fixed in the next
%version of the VDMA IP in which case this module may be removed.
% Inputs :
%   s2mm_fsync_out - The VDMA Interface's s2mm_fsync_out signal
%   s2mm_tready - The VDMA Interface's s2mm_tready signal
%   tvalid - User tvalid signal
%   s2mm_tlast - User tlast signal connected to the VDMA Interface block
%   aresetn - AXI resetn signal
%   VSIZE - Number of lines in a frame - parameter
% Outputs :
%   tready - The actual tready signal that user logic should monitor to
%   comply with AXI protocol
%   s2mm_tvalid - The tvalid signal produced that should be connected to
%   the VDMA Interface

persistent s2mm_fsync_out_pulse, s2mm_fsync_out_pulse = xl_state(false,{xlBoolean, 1, 0});
persistent s2mm_tlast_count, s2mm_tlast_count = xl_state(0,{xlUnsigned, 32, 0});


%Start of each Clock Cycle Update the state variables
if aresetn == false
    s2mm_fsync_out_pulse = false;
    s2mm_tlast_count = 0;
else
    if s2mm_tlast_count == VSIZE,
        s2mm_fsync_out_pulse = false;
    end;
    if s2mm_fsync_out == true,
        s2mm_tlast_count = 0;
        s2mm_fsync_out_pulse = true;
    end;
end;

%Update the output Signals as a consequence of state updates
if s2mm_fsync_out_pulse == true,
    s2mm_tvalid = tvalid;
    tready = s2mm_tready;
    if tvalid == true && s2mm_tready == true && s2mm_tlast == true,
        s2mm_tlast_count = s2mm_tlast_count + 1;
    end;
else
    s2mm_tvalid = false;
    tready = false;
end;

